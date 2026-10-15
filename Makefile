# Paths & Ports
PATH_TO_REF_SERVER = libs/reference-server-0.2.3.jar
REF_SERVER_PORT = 5000
PATH_TO_OWN_SERVER = za.co.wethinkcode.robots.server.Server
NEW_PATH = target/robot-world-0.0.2-jar-with-dependencies.jar
OWN_SERVER_PORT = 5000


# Docker & Registry
IMAGE_NAME := robot-worlds
HOST_PORT := 5050
CONTAINER_PORT := 5050
MVN := mvn
REGISTRY := gitlab.wethinkco.de:5050/cjc-04/brownfields-robot-worlds-cjc-04


# Versioning
VERSION := $(shell mvn help:evaluate -Dexpression=project.version -q -DforceStdout)


# Default target - runs full build and test pipeline
all: build run-unittests run-ref-tests run-own-tests package

# Project build & package

# Compile source code only
compile:
	$(MVN) compile

# Full build - compile and package with dependencies
build: compile
	@echo "Compiling project..."
	@echo "Cleaning project..."
	$(MVN) clean package -DskipTests=true

# Create JAR files including jar-with-dependencies
package:
	@echo "Packaging project..."
	$(MVN) package -DskipTests=true
	@echo "Finished packaging."

# Unit tests
run-unittests:
	@echo "Running unit tests..."
	@bash test_scripts/unit_tests

# Acceptance tests
run-ref-tests:
	@echo "Running acceptance tests against REFERENCE server..."
	@bash test_scripts/ref_tests

run-own-tests:
	@echo "Running acceptance tests against OWN server..."
	@bash test_scripts/own_tests


# Docker targets
# Build Docker image from current code
docker-build: package
	docker build -t $(IMAGE_NAME):$(VERSION) .

# Run server in Docker container
docker-run: docker-build
	docker run -d --name $(IMAGE_NAME) -p $(HOST_PORT):$(CONTAINER_PORT) $(IMAGE_NAME):$(VERSION)

# Stop and remove Docker container
docker-stop:
	docker stop $(IMAGE_NAME) 2>/dev/null || true
	docker rm $(IMAGE_NAME) 2>/dev/null || true

# View Docker container logs
docker-logs:
	docker logs -f $(IMAGE_NAME)

# Open shell in Docker container
docker-shell: docker-build
	docker run -it --rm --entrypoint /bin/bash $(IMAGE_NAME):$(VERSION)

# Push Docker image to registry
push: docker-build
	docker tag $(IMAGE_NAME):$(VERSION) $(REGISTRY)/$(IMAGE_NAME):$(VERSION)
	docker tag $(IMAGE_NAME):$(VERSION) $(REGISTRY)/$(IMAGE_NAME):latest
	docker push $(REGISTRY)/$(IMAGE_NAME):$(VERSION)
	docker push $(REGISTRY)/$(IMAGE_NAME):latest


# Acceptance tests against Docker
# Run acceptance tests against Dockerized server
test-docker: docker-build
	@echo "Running Docker acceptance tests..."
	docker run -d --name test-server -p $(HOST_PORT):$(CONTAINER_PORT) $(IMAGE_NAME):$(VERSION); \
	sleep 10; \
	SERVER_PORT=$(HOST_PORT) $(MVN) test -Dtest="za.co.wethinkcode.acceptance.*Tests*" -DfailIfNoTests=false || TEST_RESULT=$$?; \
	docker stop test-server 2>/dev/null || true; \
	docker rm test-server 2>/dev/null || true; \
	exit $${TEST_RESULT:-0}


# Version & release
# Set project version to specified VERSION parameter
set-version:
	sed -i 's|<version>[^<]*</version>|<version>$(VERSION)</version>|' pom.xml

# Display current project version
print-version:
	@echo "VERSION is $(VERSION)"

# Remove -SNAPSHOT suffix from current version
remove-snapshot:
	@if echo "$(VERSION)" | grep -q "SNAPSHOT"; then \
		echo "Removing SNAPSHOT from version..."; \
		sed -i 's|-SNAPSHOT</version>|</version>|' pom.xml; \
	else \
		echo "Version $(VERSION) is not a SNAPSHOT"; \
	fi

# Full release workflow - test, version, commit, tag, and push
release: run-ref-tests run-own-tests
	sed -i 's|<version>[^<]*</version>|<version>$(VERSION)</version>|' pom.xml
	git add pom.xml
	git commit -m "Release $(VERSION)"
	git tag -a "release-$(VERSION)" -m "Release version $(VERSION)"
	git push origin main
	git push origin release-$(VERSION)


# Help
# Display available Make targets and usage information
help:
	@echo "Usage: make [target]"
	@echo "Available targets: build, package, run-unittests, run-ref-tests, run-own-tests"
	@echo "Docker: make build, make docker-build, make docker-run, make docker-logs / docker logs -f robot-worlds, make push, make docker-stop, docker-shell, test-docker"
	@echo "Release: remove-snapshot, version, tag, release"
	@echo "Login: docker login gitlab.wethinkco.de:5050"


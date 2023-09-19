.PHONY: build

build:
	docker build --tag delivery-simulation:mac .

run:
	docker run delivery-simulation:mac

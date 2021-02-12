# Launchpad to Home Assistant

Control your [Home Assistant](https://www.home-assistant.io/) instance from [Novation Launchpad](https://novationmusic.com/en/launch) midi keyboard.

This java application allows you to bind key to an action on home assistant.


## Prerequisite

This application is based on the MIDI library [LP4J](https://github.com/OlivierCroisier/LP4J). See [here](https://github.com/totothekiller/LP4J) for my fork to handle Lanchpad Mini pad.

## Setup

See [application.yml](src/main/resources/application.yml) for full configuration.

Pads configuration :

```yml
    - name: "Name for internal use"
      x: 0                            # Pad coordinate
      y: 0                            # Pad coordinate
      domain: light                   # Service domain to call on press. See HA documentation.
      service: toggle                 # Service name to call on press.
      entityId: "light.garage_light"  # Entity in HA
```

## Run

Use maven to build this application then simply run as java program.


```bash
mvn clean package
java -jar target/launchpad-ha-control-0.0.1-SNAPSHOT.jar
```
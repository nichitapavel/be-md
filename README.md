# be-md
Web testing with selenium
# Requirements
- Ubuntu 18.04, Desktop or server edition does not matter
- Docker 18.06.1-ce, build e68fc7a ([instructions](https://docs.docker.com/install/linux/docker-ce/ubuntu/))
- docker-compose version 1.22.0, build f46880fe (instructions below)
- JDK => 1.8_181
- gradle 4.10.2 ([instructions](https://gradle.org/install/))
- git

# Install instructions
#### docker-compose
You have to download the binary, change it to execute and (optional) move it somewhere in the PATH
```bash
wget https://github.com/docker/compose/releases/download/1.22.0/docker-compose-Linux-x86_64
chmod 744 /usr/local/bin/docker-compose
sudo mv docker-compose-Linux-x86_64 /usr/local/bin/docker-compose
```

# Run instructions
### Run selenium grid instructions for Ubuntu
- clone this repository (make sure you're using the master branch)
```bash
git clone https://github.com/nichitapavel/be-md
```
- change directory to repository
```bash
cd be-md
```
- run docker containers
This will run on foreground, to stop it press CTRL + C.
The first time it will take a while until Selenium Grid is ready, this is because is downloading
docker images from the docker hub, how fast will it do it depends on your internet speed.
```bash
docker-compose up
```
- visit selenium hub console
optionaly you can visit the selenium hub page when docker compose is done, take your computer ip
(command line: `ip a`), open a browser and go to `http://<your computer ip>:4444/grid/console`

### Run the client with tests
This part actually can be done from any OS, steps can vary.

- IMPORTANT: update the build.gradle to match your config, otherwise it won't work 100%.
Update the *test {}* with:
    - GitHub oauth2 token authentication (
    [instructions](https://help.github.com/articles/creating-a-personal-access-token-for-the-command-line/))
    for api testing with gist/json
    - the full url of the selenium hub for ui testing

This is an example just change the these values with your values:
```gradle
test {
    systemProperty 'token', 'xrq66q5wd27ezd212bv32dasgyj32bg6ftyh6das'
    systemProperty 'selenium-hub', 'http://192.168.0.154:4444/wd/hub'
}
```
- For ui testing, **Create and validate** scenario, you need to change at least the *email* in
src/test/resources/qa/qa.feature, is advised to change it also for **Validate user** scenario.

- run the tests
```bash
gradle test
```
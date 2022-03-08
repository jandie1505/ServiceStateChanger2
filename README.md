# ServiceStateChanger for CloudNet
ServiceStateChanger is a plugin that can be used to set the state of a service to ingame state (or any other state).
## Dependencies
- CloudNet v3
## Installation
1. Download the plugin *for the correct minecraft version* from the releases page
2. Put the downloaded jar file into your plugin folder
3. Restart the server
## Commands and Permissions
| Command | Permission | Description |
|--|--|--|
| `/servicestate` | servicestate.get | Shows the current state |
| `/servicestate INGAME [startNewService]` | servicestate.setIngame | Set the state to ingame and start a new service of the same task |
| `/servicestate LOBBY` | servicestate.setLobby | Set the state to lobby |
| `/servicestate CUSTOM <Text>` | servicestate.setCustom | Set the state to a custom state (this custom state is the same as the lobby state with another name) |

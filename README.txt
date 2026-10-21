Web Rocket (Fabric, Minecraft 1.21.4)

Press = in game to open the menu (rebind in Options > Controls). The button turns the mod on/off.

BUILD OPTION A - on your PC:
  1. Install JDK 21 (adoptium.net).
  2. Copy gradlew, gradlew.bat and the gradle/ folder from FabricMC/fabric-example-mod into this folder.
  3. Run: gradlew build   (Mac/Linux: ./gradlew build)
  4. Jar: build/libs/webrocket-1.21.4-1.1.0.jar (not the -sources one)

BUILD OPTION B - no installs:
  Upload this folder to a new GitHub repo (include the hidden .github folder).
  Open the Actions tab > build > run it, then download the "webrocket-jar" artifact.

INSTALL: jar + Fabric API in your mods folder, Fabric Loader 0.16.10+.
Note: in multiplayer the server decides movement, so install it on the server too.

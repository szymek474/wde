FOCUS 1v1 - mod Fabric 1.21.4 (tylko klient)

Prawy klik na gracza = tryb 1v1 (widzisz i bijesz tylko jego). R = wyjscie.

JAK ZBUDOWAC:
1. Zainstaluj JDK 21.
2. Wejdz na https://fabricmc.net/develop/template/ , wybierz 1.21.4, pobierz szablon
   i skopiuj z niego do tego folderu pliki: gradlew, gradlew.bat oraz folder gradle/
   (to wrapper Gradle - nie da sie go wygenerowac offline).
3. W folderze projektu: ./gradlew build   (Windows: gradlew.bat build)
4. Gotowy plik: build/libs/focusmod-1.0.0.jar -> wrzuc do .minecraft/mods
   (potrzebujesz Fabric Loader 1.21.4 + Fabric API).

Test w IDE: ./gradlew runClient

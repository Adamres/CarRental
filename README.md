# CarRental
**Használt fejlesztői környezet:** IntelliJ IDEA<br>
**Használt technológiák:** Spring Boot 2, Spring Security, Spring MVC, Docker<br>
**Frontend:** Thymeleaf<br>
**Adatbázis engine:** PostgreSQL

# Útmutató
## Projekt bekonfigurálása első használat előtt<br>
 
  1. Beállítjuk az adatbázis elérési adatokat ```src/main/resources/application.properties``` fájlban módosíthatunk minden adatbázis eléréssel kapcsolatos információt
  1. Érdemes először rányomni a Reload All Maven Projects gombra IntelliJ esetén
  2. mvn install
  3. Futtatás előtt létrehozni az adatbázist és a táblákat, beszúrásokat, amelyek kódjai megtalálhatóak az ```src/main/resources``` mappában. Ha nem hozzuk létre a táblákat első indítás előtt, akkor a hibernate ezt megteszi helyettünk, de érdemes nekünk lefuttatni először az sql fájlokat.  Viszont magát a ```car_rental``` nevű adatbázist muszáj létrehozni.
  5. Le lehet simán is futtatni a projektet, de maven-ből is indíthatjuk: mvn spring-boot:run
  
## Lehetőség van docker segítségével elindítani a projektet, ebben az esetben

 1. mvn install, ekkor létrejön a target mappában a car-rental-docker nevű jar fájl
 2. Terminálon belül navigáljunk a projekt mappájába (carRental, ahol vannak a docker fájlok is)
 3. Adjuk ki a következő két parancsot a terminálon belül:<br> 
     Buildeléshez: &nbsp; ```docker build -t car-rental-docker.jar .``` <br>
     Futtatáshoz:  &nbsp;   ```docker run --net=host -p 8081:8080 -t car-rental-docker.jar``` <br>



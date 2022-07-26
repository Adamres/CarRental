# CarRental
**Használt fejlesztői környezet:** IntelliJ IDEA<br>
**Használt technológiák:** Spring Boot 2, Spring Security, Spring MVC, Docker<br>
**Frontend:** Thymeleaf<br>
**Adatbázis engine:** PostgreSQL

# Útmutató
## Projekt bekonfigurálása első használat előtt<br>
 
  1. Érdemes először rányomni a Reload All Maven Projects gombra IntelliJ esetén
  2. mvn install
  3. Futtatás előtt létrehozni az adatbázist és a táblákat, amelyek kódjai megtalálhatóak az ```src/main/resources``` mappában. Ha nem hozzuk létre a táblákat első indítás előtt, akkor a hibernate ezt megteszi helyettünk, de érdemes nekünk lefuttatni először és beszúrni az insert.sql-ben levő adatokat is. Viszont magát a ```car_rental``` nevű adatbázist muszáj létrehozni.
  5. Le lehet simán is futtatni, de maven-ből is indíthatjuk: mvn spring-boot:run
  
## Lehetőség van docker segítségével elindítani a projektet, ebben az esetben

 1. mvn install, ekkor létrejön a target mappában a car-rental-docker nevű jar fájl
 2. Terminálon belül navigáljunk a projekt mappájába (carRental, ahol vannak a docker fájlok is)
 3. Adjuk ki a következő két parancsot a terminálon belül:<br> 
     Buildeléshez: &nbsp; ```docker build -t car-rental-docker.jar .``` <br>
     Futtatáshoz:  &nbsp;   ```docker run --net=host -p 8081:8080 -t car-rental-docker.jar``` <br>



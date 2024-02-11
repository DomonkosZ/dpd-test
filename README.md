## DPD Teszt feladat

Az alkalmazások rendelkeznek Dockerfile-al így a docker-compose.yml leírása alapján a repository
clone után az alábbi parancsal indítható a teszt feladat:

`docker-compose up --build`

A frontend a http://localhost:3000/ címen elérhető ha a build végzett és sikeresen elindultak a
containerek.

Az szoftver az alábbi két fő modulból áll:

### dpd-backend

Ez a repository egy Java alapú Spring Boot backend alkalmazást tartalmaz, amely REST API-n keresztül
kommunikál a frontenddel. Az alkalmazás az adatbázissal Hibernate segítségével kommunikál, és az
adatbázis verziókövetését a Liquibase segítségével kezeli.

Az alkalmazás indulásával egy előre meghatározott adat mennyiséget be is tölt, hogy már meglévő
ügyfelekkel induljon az alkalmazás kezdő képernyője.

#### Jellemzők

* Java, Spring Boot Backend: Az alkalmazás Java nyelven és Spring Boot keretrendszer segítségével
  készült.
* REST API: Az alkalmazás REST API-n keresztül fogadja a frontendről érkező kéréseket.
* Adatbázis Kommunikáció: Az alkalmazás Hibernate segítségével kommunikál az adatbázissal.
* Verziókövetés: Az adatbázis verziókövetése és sémafrissítései a Liquibase segítségével történnek.
* Egység Tesztek: Az alkalmazás egység tesztekkel van lefedve a megbízhatóság biztosítása érdekében.

#### Telepítési útmutató

1. Navigálj a projy ekt könyvtárába.
2. Ellenőrizd, hoga Java és a Maven telepítve van-e a rendszereden.
3. Állítsd be az adatbázis kapcsolatot a application.properties állományban.
4. Futtasd az alkalmazást a Maven segítségével (mvn spring-boot:run).
5. Az alkalmazás elérhető lesz a http://localhost:8080 címen.

#### Elérhető végpontok:

- /api/customers (POST) - Új ügyfél rögzítése
- /api/customers/{booleanFlag} (PUT) - Meglévő ügyfél módosítása / GDPR átalakítás
- /api/customers (GET) - Összes meglévő ügyfél listázása

### dpd-frontend

Ez a repository egy React alapú frontend alkalmazást tartalmaz, ami ügyfél adatok kezelésére
szolgál. Tartalmaz olyan funkciókat, mint ügyfél adatok hozzáadása, azok listázása szerkeszthető
táblázatban, valamint lehetőséget nyújt az adatvédelmi szabályozásoknak való megfelelésre.

#### Telepítési útmutató

1. Navigálj a projekt könyvtárába.
2. Futtasd a npm install parancsot a projekt függőségeinek telepítéséhez.
3. Futtasd a npm start parancsot a fejlesztői kiszolgáló indításához.
4. Nyisd meg a böngésződet, és navigálj a http://localhost:3000 címre az alkalmazás megtekintéséhez.





# Micro Finance Tracking (Mikro Finans Takip)

Bu proje, kullanıcıların gelir ve giderlerini takip edebileceği, harcama kategorilerini analiz edebileceği ve aylık tasarruf raporları oluşturabileceği bir Java tabanlı finans yönetim sistemidir. Proje, RESTful API yapısı ile çalışır ve tüm işlemler veritabanına kaydedilir.

## Proje Özellikleri

* **Kullanıcı Yönetimi:** Kullanıcı kaydı ve giriş işlemleri. Her kullanıcıya özel gelir/gider kayıtları.
* **Gelir/Gider Yönetimi:** Gelir ve gider ekleme, listeleme, güncelleme ve silme (CRUD) işlemleri. Her kayıt için kategori, tutar, açıklama ve tarih bilgisi.
* **Aylık Raporlama:** Toplam gelir, toplam gider, tasarruf miktarı ve en çok harcama yapılan kategori gibi bilgileri içeren aylık finansal özetler (JSON formatında).
* **Veri Saklama:** Tüm veriler bir veritabanında güvenli bir şekilde saklanır.
* **API Erişimi:** Uygulama ile etkileşim, RESTful API endpoint'leri aracılığıyla sağlanır.

## Kullanılan Teknolojiler

* **Java 17+:** Projenin geliştirildiği programlama dili.
* **Spring Boot 3.x:** RESTful API'ler ve uygulamanın temel çatısı için kullanılan framework.
* **Spring Data JDBC:** Veritabanı etkileşimini kolaylaştıran Spring modülü.
* **H2 Database:** Geliştirme ve test ortamı için kullanılan hafif, in-memory (bellekte çalışan) veritabanı. (İsteğe bağlı: PostgreSQL/MySQL gibi harici bir veritabanına kolayca geçiş yapılabilir.)
* **Maven:** Proje bağımlılık yönetimi ve build aracı.
* **Lombok:** Getter, setter gibi boilerplate kodları otomatik oluşturan kütüphane (isteğe bağlı).
* **Postman / Swagger:** API testleri ve dokümantasyonu için kullanılan araçlar.

## Kurulum Adımları

Projeyi yerel makinenizde kurmak ve çalıştırmak için aşağıdaki adımları izleyin:

1.  **Gereksinimler:**
    * Java Development Kit (JDK) 17 veya üzeri kurulu olmalı.
    * Maven kurulu olmalı (Spring Boot projesi oluştururken otomatik gelir).
    * Bir IDE (IntelliJ IDEA, Eclipse, VS Code) kurulu olmalı.

2.  **Projeyi Klonlayın:**
    ```bash
    git clone [https://github.com/neslihangunduz/MicroFinanceTracking.git](https://github.com/KULLANICI_ADINIZ/micro-finans-takip.git)
    cd micro-finans-takip
    
    

3.  **IDE ile Açın:**
    * IntelliJ IDEA'yı açın.
    * "Open" (Aç) seçeneğini kullanarak klonladığınız `MicroFinanceTracking` klasörünü seçin.
    * IDE, Maven projesini otomatik olarak tanıyacak ve bağımlılıkları indirecektir.

4.  **Veritabanı Yapılandırması:**
    * Proje, `src/main/resources/application.properties` dosyasında H2 veritabanı için yapılandırılmıştır.
    * Uygulama her başladığında `src/main/resources/schema.sql` ve `src/main/resources/data.sql` dosyaları otomatik olarak çalıştırılarak veritabanı şeması oluşturulur ve örnek veriler eklenir.

## Uygulamayı Çalıştırma

1.  IDE'nizde (IntelliJ IDEA), `src/main/java/com/example/micro_finance_tracking/MicroFinanceTrackingApplication.java` dosyasını açın.
2.  `main` metodunun yanındaki yeşil "Play" (Oynat) butonuna tıklayarak uygulamayı başlatın.
3.  Uygulama başarıyla başladığında konsolda "Started MicroFinanceTrackingApplication in X seconds" benzeri bir mesaj göreceksiniz.
4.  H2 veritabanı konsoluna `http://localhost:8080/h2-console` adresinden erişebilirsiniz. Bağlantı bilgileri: `JDBC URL: jdbc:h2:mem:financedb`, `User Name: sa`, `Password: ` (boş).

## API Endpoint'leri

Uygulamanın RESTful API'leri aşağıdaki endpoint'ler aracılığıyla erişilebilir:

### Kullanıcı İşlemleri (`/api/users`)

* **Kullanıcı Kaydı**
    * **URL:** `/api/users/register`
    * **Metot:** `POST`
    * **Açıklama:** Yeni bir kullanıcı kaydeder.
    * **Giriş Parametreleri (Request Body - JSON):**
        ```json
        {
            "username": "yeni_kullanici",
            "password": "sifre123",
            "email": "yeni@example.com"
        }
        ```
    * **Çıkış Parametreleri (Response Body - JSON):**
        * Başarılı durum (HTTP 201 Created): Kaydedilen kullanıcı nesnesi.
        * Hata durumu (HTTP 400 Bad Request): Hata mesajı (örn: "Bu kullanıcı adı zaten mevcut.").
    * **Örnek İstek (Postman):**
        ```
        POST http://localhost:8080/api/users/register
        Content-Type: application/json

        {
            "username": "testuser",
            "password": "password123",
            "email": "test@example.com"
        }
        ```
    * **Örnek Cevap (Başarılı):**
        ```json
        {
            "id": 1,
            "username": "testuser",
            "password": "password123",
            "email": "test@example.com"
        }
        ```

* **Kullanıcı Girişi**
    * **URL:** `/api/users/login`
    * **Metot:** `POST`
    * **Açıklama:** Mevcut bir kullanıcının giriş yapmasını sağlar.
    * **Giriş Parametreleri (Request Body - JSON):**
        ```json
        {
            "username": "mevcut_kullanici",
            "password": "mevcut_sifre"
        }
        ```
    * **Çıkış Parametreleri (Response Body - JSON):**
        * Başarılı durum (HTTP 200 OK): Giriş yapan kullanıcı nesnesi.
        * Hata durumu (HTTP 401 Unauthorized): "Kullanıcı adı veya şifre hatalı." mesajı.
    * **Örnek İstek (Postman):**
        ```
        POST http://localhost:8080/api/users/login
        Content-Type: application/json

        {
            "username": "user1",
            "password": "pass1"
        }
        ```
    * **Örnek Cevap (Başarılı):**
        ```json
        {
            "id": 1,
            "username": "user1",
            "password": "pass1",
            "email": "user1@example.com"
        }
        ```

### İşlem Yönetimi (`/api/transactions`)

* **Yeni İşlem Ekleme**
    * **URL:** `/api/transactions/add`
    * **Metot:** `POST`
    * **Açıklama:** Yeni bir gelir veya gider işlemi ekler.
    * **Giriş Parametreleri (Request Body - JSON):**
        ```json
        {
            "userId": 1,
            "categoryId": 4,  
            "amount": 150.75,
            "description": "Akşam yemeği alışverişi",
            "date": "2025-05-29",
            "type": "GİDER"
        }
        ```
    * **Çıkış Parametreleri (Response Body - JSON):**
        * Başarılı durum (HTTP 201 Created): Eklenen işlem nesnesi.
        * Hata durumu (HTTP 400 Bad Request): Hata mesajı (örn: "İşlem miktarı sıfırdan büyük olmalı.").

* **Kullanıcıya Ait Tüm İşlemleri Listeleme**
    * **URL:** `/api/transactions?userId={userId}`
    * **Metot:** `GET`
    * **Açıklama:** Belirli bir kullanıcıya ait tüm gelir ve gider işlemlerini listeler.
    * **Giriş Parametreleri (Query Parameter):** `userId` (Long)
    * **Çıkış Parametreleri (Response Body - JSON Array):** İşlem nesnelerinin listesi.

* **Aylık İşlemleri Listeleme**
    * **URL:** `/api/transactions/monthly?month={month}&year={year}&userId={userId}`
    * **Metot:** `GET`
    * **Açıklama:** Belirli bir ay ve yıla ait, belirli bir kullanıcının tüm gelir ve gider işlemlerini listeler.
    * **Giriş Parametreleri (Query Parameters):** `month` (int), `year` (int), `userId` (Long)
    * **Çıkış Parametreleri (Response Body - JSON Array):** İşlem nesnelerinin listesi.

### Raporlama İşlemleri (`/api/reports`)

* **Aylık Finansal Özet**
    * **URL:** `/api/reports/users/{id}/summary?year={year}&month={month}`
    * **Metot:** `GET`
    * **Açıklama:** Belirli bir kullanıcı ve ay için toplam gelir, toplam gider, tasarruf miktarı ve en çok harcama yapılan kategori gibi finansal özet bilgilerini sağlar.
    * **Giriş Parametreleri (Path Variable & Query Parameters):** `id` (Long - kullanıcı ID'si), `year` (int), `month` (int)
    * **Çıkış Parametreleri (Response Body - JSON):**
        ```json
        {
            "totalIncome": 5000.0,
            "totalExpense": 2700.0,
            "savings": 2300.0,
            "mostSpentCategory": "Kira",
            "categoryExpenses": {
                "Market": 700.0,
                "Kira": 2000.0,
                "Ulaşım": 150.0
            }
        }
        ```

## Örnek Kullanım Senaryoları

Bu bölümde, uygulamanın temel kullanım akışlarını açıklayabilirsiniz.

1.  **Kullanıcı Kaydı ve Girişi:**
    * Yeni bir kullanıcı (`testuser`) sisteme kaydolur.
    * `testuser`, kullanıcı adı ve şifresiyle sisteme giriş yapar.
2.  **Gelir Ekleme:**
    * `testuser`, "Maaş" kategorisinde 5000 TL gelir ekler.
    * `testuser`, "Serbest Kazanç" kategorisinde 1000 TL ek gelir ekler.
3.  **Gider Ekleme:**
    * `testuser`, "Market" kategorisinde 350 TL gider ekler.
    * `testuser`, "Fatura" kategorisinde 200 TL gider ekler.
    * `testuser`, "Ulaşım" kategorisinde 100 TL gider ekler.
4.  **İşlemleri Görüntüleme:**
    * `testuser`, tüm gelir ve gider işlemlerini listeler.
    * `testuser`, Mayıs 2025 ayına ait tüm işlemlerini görüntüler.
5.  **Aylık Rapor Görüntüleme:**
    * `testuser`, Mayıs 2025 ayına ait finansal özet raporunu (toplam gelir, toplam gider, tasarruf, en çok harcanan kategori) görüntüler.
6.  **İşlem Güncelleme/Silme:**
    * `testuser`, yanlış girilen bir market harcamasının tutarını günceller.
    * `testuser`, yanlışlıkla girilen bir işlemi siler.

---

### 2. Veritabanı Dosyası ve Şema Tasarımı (Deponun Planı)

* **`schema.sql` ve `data.sql`:** Bu dosyalar zaten projenizin `src/main/resources` klasöründe bulunuyor olmalı. Bunlar, veritabanı tablolarınızı oluşturan ve örnek verileri ekleyen SQL komutlarını içerir. Bu dosyaları teslimat için proje klasörünüzde bırakın.
* **ER Diyagramı veya Tablo Açıklamaları:**
    * **ER Diyagramı:** Bu, veritabanı tablolarınızın ve aralarındaki ilişkilerin görsel bir temsilidir.
        * **Nasıl Yapılır:** `draw.io` (yeni adıyla diagrams.net) veya Lucidchart gibi ücretsiz online araçları kullanabilirsiniz.
            1.  `draw.io` adresine gidin.
            2.  "Create New Diagram" (Yeni Diyagram Oluştur) seçeneğini seçin.
            3.  Sol taraftaki şekil kütüphanelerinden "Entity Relation" (Varlık İlişkisi) şekillerini bulun (genellikle "UML" veya "Flowchart" altında olabilir).
            4.  `Users`, `Categories`, `Transactions` tablolarınızı temsil eden varlık kutularını sürükleyip bırakın.
            5.  Her kutuya tablolarınızdaki sütunları (id, username, amount vb.) ve veri tiplerini ekleyin.
            6.  Tablolar arasındaki ilişkileri (örneğin, `Transactions` tablosundan `Users` tablosuna bir okla `user_id` ile `id` arasındaki bağlantı) çizin.
            7.  Bitirdiğinizde, diyagramı bir resim dosyası (PNG, JPG) veya PDF olarak dışa aktarın (`File > Export as > PNG/PDF`).
    * **Tablo Açıklamaları (Alternatif):** Eğer ER diyagramı çizmekte zorlanırsanız, tablolarınızı metin olarak açıklayabilirsiniz. Bu, bir PDF dokümanına eklenebilir.

        ```
        Veritabanı Tabloları ve Yapıları:

        1.  Users Tablosu: Kullanıcı bilgilerini saklar.
            - id: BIGINT, PRIMARY KEY, AUTO_INCREMENT (Benzersiz kullanıcı kimliği)
            - username: VARCHAR(255), NOT NULL, UNIQUE (Kullanıcı adı)
            - password: VARCHAR(255), NOT NULL (Kullanıcı şifresi)
            - email: VARCHAR(255), NOT NULL, UNIQUE (Kullanıcı e-postası)

        2.  Categories Tablosu: Gelir ve gider kategorilerini saklar.
            - id: BIGINT, PRIMARY KEY, AUTO_INCREMENT (Benzersiz kategori kimliği)
            - name: VARCHAR(255), NOT NULL, UNIQUE (Kategori adı, örn: 'Maaş', 'Market')
            - type: VARCHAR(50), NOT NULL ('GELİR' veya 'GİDER')

        3.  Transactions Tablosu: Tüm gelir ve gider işlemlerini saklar.
            - id: BIGINT, PRIMARY KEY, AUTO_INCREMENT (Benzersiz işlem kimliği)
            - user_id: BIGINT, NOT NULL, FOREIGN KEY (users tablosuna referans)
            - category_id: BIGINT, NOT NULL, FOREIGN KEY (categories tablosuna referans)
            - amount: DOUBLE, NOT NULL (İşlem miktarı)
            - description: VARCHAR(255) (İşlem açıklaması)
            - date: DATE, NOT NULL (İşlem tarihi)
            - type: VARCHAR(50), NOT NULL ('GELİR' veya 'GİDER')
        ```

---

### 3. Proje Dokümantasyonu 

 



        
        +-------------------+       +-------------------+       +-------------------+
        |      User         |       |     Category      |       |    Transaction    |
        +-------------------+       +-------------------+       +-------------------+
        | - id: Long        |       | - id: Long        |       | - id: Long        |
        | - username: String|       | - name: String    |       | - userId: Long    |
        | - password: String|       | - type: String    |       | - categoryId: Long|
        | - email: String   |       +-------------------+       | - amount: Double  |
        +-------------------+                                   | - description: String|
        | + registerUser()  |                                   | - date: LocalDate |
        | + loginUser()     |                                   | - type: String    |
        +-------------------+                                   +-------------------+
                                                                | + addTransaction()|
                                                                | + updateTransaction()|
                                                                | + deleteTransaction()|
                                                                +-------------------+
              ^                                   ^
              | uses                              | uses
        +-------------------+       +-------------------+       +-------------------+
        |   UserRepository  |       | CategoryRepository|       | TransactionRepository|
        +-------------------+       +-------------------+       +-------------------+
        | (CrudRepository)  |       | (CrudRepository)  |       | (CrudRepository)  |
        +-------------------+       +-------------------+       +-------------------+
              ^                                   ^                       ^
              | calls                             | calls                 | calls
        +-------------------+       +-------------------+       +-------------------+
        |    UserService    |       |   ReportService   |       | TransactionService|
        +-------------------+       +-------------------+       +-------------------+
        | + registerUser()  |       | + getMonthlySummary()|    | + addTransaction()|
        | + loginUser()     |       +-------------------+       | + getAllTransactionsForUser()|
        +-------------------+                                   | + updateTransaction()|
                                                                | + deleteTransaction()|
                                                                +-------------------+
              ^                                   ^                       ^
              | handles requests                  | handles requests      | handles requests
        +-------------------+       +-------------------+       +-------------------+
        |   UserController  |       |   ReportController|       | TransactionController|
        +-------------------+       +-------------------+       +-------------------+
        | + register()      |       | + getSummary()    |       | + add()           |
        | + login()         |       +-------------------+       | + list()          |
        +-------------------+                                   | + update()        |
                                                                | + delete()        |
                                                                +-------------------+
        


    
    Kullanım Senaryoları:

    1.  Kullanıcı Kaydı ve Girişi:
        * Kullanıcı, kayıt formunu doldurarak yeni bir hesap oluşturur (kullanıcı adı, şifre, e-posta).
        * Sistem, kullanıcı bilgilerini veritabanına kaydeder ve başarılı kayıt mesajı döner.
        * Kullanıcı, kayıtlı kullanıcı adı ve şifresiyle sisteme giriş yapar.
        * Sistem, kimlik bilgilerini doğrular ve başarılı giriş mesajı ile kullanıcı oturumunu başlatır.

    2.  Gelir Ekleme:
        * Giriş yapmış kullanıcı, "Gelir Ekle" ekranına gider.
        * Gelir tutarını, açıklamasını, tarihini ve kategorisini (örn: Maaş, Serbest Kazanç) girer.
        * Sistem, geliri kullanıcının hesabına kaydeder ve onay mesajı gösterir.

    3.  Gider Ekleme:
        * Giriş yapmış kullanıcı, "Gider Ekle" ekranına gider.
        * Gider tutarını, açıklamasını, tarihini ve kategorisini (örn: Market, Fatura, Ulaşım) girer.
        * Sistem, gideri kullanıcının hesabına kaydeder ve onay mesajı gösterir.

    4.  İşlemleri Görüntüleme:
        * Giriş yapmış kullanıcı, "İşlemlerim" ekranına gider.
        * Sistem, kullanıcının tüm gelir ve gider işlemlerini (tarih, kategori, tutar, açıklama) listeler.
        * Kullanıcı, belirli bir ay veya yıla göre işlemleri filtreleyebilir.

    5.  Aylık Finansal Özet Görüntüleme:
        * Giriş yapmış kullanıcı, "Raporlar" ekranına gider.
        * Belirli bir ay ve yıl seçer.
        * Sistem, seçilen ay için kullanıcının toplam gelirini, toplam giderini, tasarruf miktarını ve en çok harcama yaptığı kategoriyi hesaplar ve görüntüler.

    6.  İşlem Güncelleme:
        * Kullanıcı, işlem listesinden bir işlemi seçer.
        * İşlemin tutarını, açıklamasını veya kategorisini günceller.
        * Sistem, işlemi veritabanında günceller ve onay mesajı gösterir.

    7.  İşlem Silme:
        * Kullanıcı, işlem listesinden silmek istediği bir işlemi seçer.
        * Sistemin onayını aldıktan sonra, işlemi veritabanından siler ve onay mesajı gösterir.
   


# The Java Servlet application. Servlet Architecture Overview #
## Application using Spring MVC with annotaions ##

## 1. Application ##
### Model: ###
- The model is responsible for representing the application's data. The model is represented by the Post class, which includes fields such as id,  
 content, and removed. This class also encapsulates business logic, such as methods for marking a post as removed.  
- The model interacts with the database through the repository.  

### View: ###  
- In application, the view is likely formed on the client side (e.g., frontend or mobile application) that interacts with API.  
- When using Spring MVC, if HTML needs to be returned, templates like Thymeleaf can be used. However, application primarily creates a RESTful API   
that returns JSON.  

### Controller:  ###

- Controller (PostController) handle incoming HTTP requests. They accept data from the client, invoke corresponding services to process   
the logic, and return the results.  
- Controller handle exceptions, such as NotFoundException, to return appropriate HTTP responses.  

## 2. Service ##
### Services: ###
- The service (PostService) contains the application's business logic. It processes requests from controllers, interacts with repositories   
for data access, and can include additional checks and data processing.  
- The service is responsible for managing the state of data and handling exceptions and business rules.  

## 3. Data Access ##
### Repositories: ###
- The repository (PostRepository) is responsible for data access. In your case, it uses a ConcurrentHashMap to store posts in memory.  
- Repository provides methods for retrieving, saving, and deleting model objects.  

## 4. Exception Handling and Error Management ##
### Exception Handling: ###
- Controller uses the @ExceptionHandler annotation to manage such exceptions and return appropriate HTTP status and error messages.  

## 5. Configuration ##
### Application Configuration: ###
- The WebConfig class configures the application, including necessary converters like GsonHttpMessageConverter for converting objects to JSON.  
- ApplicationInitializer initializes the application context and sets up the DispatcherServlet, which manages the processing of incoming requests.  

## 6. Thread Safety ##
- The use of ConcurrentHashMap and AtomicLong in PostRepository ensures thread safety, allowing the data store to be safely used in a multi-threaded environment.  

## 7. Dependencies ##
- Application relies on Spring libraries, such as Spring Web for creating RESTful APIs, and potentially Gson for data conversion.  

# Архитектура приложения #

## 1. Приложение ##
## Модель (Model): ##

- Модель отвечает за представление данных приложения и представлена классом Post, который содержит поля, такие как id, content и removed.  
- Этот класс также включает бизнес-логику, например, методы для пометки поста как удаленного.  
- Модель может взаимодействовать с базой данных через репозиторий.  

## Вид (View): ##

- В приложении Вид формируется на стороне клиента (например, фронтенд или мобильное приложение), которое обращается к API.  
- При использовании Spring MVC, если необходимо вернуть HTML, используется RESTful API, возвращая JSON.  

## Контроллер (PostController): ##

- Контроллер (PostController) обрабатывает входящие HTTP-запросы, принимает данные от клиента, вызывает соответствующие сервисы для обработки логики  
и возвращает результаты.  
- Контроллер обрабатывает исключения, например, NotFoundException, чтобы вернуть корректные HTTP-ответы.  

## 2. Serviсe ##
### Сервис (PostService): ###
- Сервис (PostService) содержит бизнес-логику приложения. Он обрабатывает запросы от контроллера, взаимодействует с репозиторием для доступа к данным   
и может включать дополнительные проверки и обработку данных.  
- Сервис отвечает за управление состоянием данных, а также за обработку исключений и бизнес-правил.  

## 3. Доступ к данным ##
### Репозиторий (PostRepository): ###  
- Репозиторий (PostRepository) отвечает за доступ к данным, используется ConcurrentHashMap для хранения постов в памяти.  
- Репозиторий обеспечивает методы для получения, сохранения и удаления объектов модели.  

## 4. Исключения и обработка ошибок ##
- Пользовательское исключение NotFoundException, которое используется для обработки случаев, когда ресурс не найден.   
- Контроллер использует аннотацию @ExceptionHandler для управления исключениями и возврата корректного HTTP-статуса и сообщений об ошибках.  

## 5. Конфигурация ##
- Класс WebConfig настраивает приложение, включая необходимые конвертеры, такие как GsonHttpMessageConverter для преобразования объектов в JSON.  
- ApplicationInitializer инициализирует контекст приложения и настраивает DispatcherServlet, который управляет обработкой входящих запросов.  

## 6. Потокобезопасность ##
- Использование ConcurrentHashMap и AtomicLong в PostRepository обеспечивает потокобезопасность, что позволяет безопасно использовать хранилище данных в многопоточной среде.  
## 7. Зависимости ##
- Приложение зависит от библиотек Spring, таких как Spring Web для создания RESTful API и возможно, Gson для преобразования данных.  
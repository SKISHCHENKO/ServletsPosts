## The Java Servlet application. Servlet Architecture Overview ## 


### Servlet Class (MainServlet) ###  
- The MainServlet class extends HttpServlet, serving as the main entry point for handling HTTP requests related to posts.
- It initializes necessary components in the init() method, including the PostController, PostService, and PostRepository.
- It overrides the service() method to handle incoming HTTP requests, determining the type of request (GET, POST, DELETE) 
and delegating the processing to specific handler methods.

### 2. Request Handling ### 
- Method Dispatching: The service() method uses a switch statement to dispatch requests based on the HTTP method. This 
improves clarity and separates the logic for each request type.
- Handler Methods: Each HTTP method has its own handler
- GET Requests: Handled by handleGetRequest(), which manages both fetching all posts and fetching a specific post by ID.
- POST Requests: Handled by handlePostRequest(), which is responsible for creating new posts.
- DELETE Requests: Handled by handleDeleteRequest(), which manages the deletion of a post by ID.

### 3. Controller (PostController) ###  
The PostController is responsible for the application's business logic related to posts. It handles requests from the servlet
 and interacts with the PostService to perform operations such as retrieving all posts, getting a post by ID, saving a new post, 
 and removing a post.

### 4. Service (PostService) ###  
The PostService class encapsulates the core business logic and interacts with the PostRepository for data management. It defines
 methods for CRUD operations and ensures that the business rules are applied.

### 4. Repository (PostRepository) ###  
The PostRepository is responsible for data access and storage. It manages the posts, typically interacting with a data source 
(in-memory data structure, database, etc.) to perform CRUD operations.

### 5. Model (Post) ###  
Models: The data structures used to represent the application's entities (Post). Models contain properties (fields) and methods
 relevant to the data they represent, usually corresponding to database tables or documents.
 
### 6. Exception (Error Handling) ###  
- The servlet includes basic error handling. If an exception occurs during request processing, it catches the exception, prints 
the stack trace, and sets the response status to 500 Internal Server Error.
- Specific conditions (like invalid IDs) return different HTTP status codes (e.g., 400 Bad Request for certain invalid inputs 
and 404 Not Found for non-existing resources).

## Dependency Injection via Annotation Config ##

final var context = new AnnotationConfigApplicationContext("Net");  
controller = context.getBean("postController", PostController.class);  

Using: @Controller @Service @Repository

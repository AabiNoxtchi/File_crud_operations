# Getting Started

This project demonstrates working with files

### ImageController
- exposes endpoints to perform create, update, read one, and read all operations
- Post endpoint to save new image
- Put endpoint to update existing, takes an id as path variable, 
  for sake of demonstration if an image with that id is not found a new image will be saved
- Post and Put endpoints will throw an exception in case of 
    - null or empty image
    - file is not of type image
    - file size is bigger than given size in application.properties file, example :
        ```
      #file upload limits
      spring.servlet.multipart.max-file-size=2MB
      spring.servlet.multipart.max-request-size=2MB
      ```
- Get endpoint that takes an id as path variable, and return the image byte[] as MediaType.IMAGE_JPEG_VALUE
  and HttpStatus.NOT_FOUND if not found
- Get endpoint to get all the images with all the data(name, contentType and the image) as a list,
  - if no records are found(the db table is empty it could return :
    - Collections.EMPTY_LIST (this approach is selected for this program))
    - or HttpStatus.NOT_FOUND
  - this endpoint transforms byte[] of the image to Base64 string :
    - to be able to send it in MediaType.APPLICATION_JSON_VALUE type response
    - transformation is done in Base64ImageDTO record to hold the new data form in the response
  

### default spring Boot configurations for file and request size :
- Max File Size: The default max file size is set to 1MB.
- Max Request Size: The default max request size is also set to 1MB.
- For unlimited upload file size set them to -1, but this is considered **bad practice**, 
  as the server becomes more vulnerable to memory leaks and/or malicious files


### @lob annotation
- LOB stands for Large Object. It's a data type used to store large amounts of data, typically binary or textual content,
  that can't be efficiently stored in regular database fields due to its size. There are two main types of LOBs:
  - BLOB (Binary Large Object):
    Used for storing binary data like images, audio files, video files, or other large binary objects.
    In relational databases, BLOBs can hold data such as media files, documents, etc.
  - CLOB (Character Large Object):
    Used for storing large amounts of text data.
    CLOBs are typically used for storing long text documents or string-based data that exceeds the limit of a standard text or varchar field.

- @Lob: This annotation is used to indicate that a field in an entity should be treated as a Large Object 
  either BLOB or CLOB, depending on the type of data.
- @Column: also can be used to specify database-specific characteristics like column length and type here.

 
### Analyzing database column space used by LOB objects
- this system uses postgreSQL database
- PostgreSQL has a large object support feature, allowing to store large binary objects (like files, images, etc.)
  in a special table called pg_largeobject.
- These large objects are stored as chunks (with each chunk typically 2 KB in size) in the pg_largeobject system table,
  and references to them are stored in user tables.
- compression is not automatically applied to the large object data when stored in the database.
- PostgreSQL uses a feature called TOAST (The Out-of-Row Storage) to store large data efficiently, including large text
  and binary data(regular text and bytea data types, but this does not apply to LOBs stored using the pg_largeobject system).
  TOAST automatically compresses large values (including TEXT, BYTEA, and VARCHAR data types) when they
  exceed a certain size threshold, typically 2 KB.
- Important: When working with large objects in the pg_largeobject system, PostgreSQL does not apply TOAST compression.

- to check used column sizes in the database using psql commandLine :
    - insert a file in the system and identify the id
    - open your psql commandLine
    - `\c your_database_name;` to use your database
    - `SELECT your_column_name FROM your_table_name WHERE id = the_saved_id;` to identify the Ioid: 
    - Ioid : This is the unique identifier for the large object in PostgreSQL.
    - to get the size of your lob entry :
      ```
      SELECT lo.loid, SUM(octet_length(data)) AS total_size 
           FROM pg_largeobject lo 
           WHERE loid = the_Ioid_you_retrieved_in_previous_step 
           GROUP BY loid;```
    - the returned value is the total size of the large object in bytes, including all of its chunks.


- using `java.util.zip.Deflater` and `java.util.zip.Inflater` to compress files in CompressorUtil class reduces used database space


### Media types
- defining produces type is beneficial for frontend apps and web clients such as browsers to identify the returned file and 
 to be able to display it correctly
- `@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)` for Get endpoint returning json for example
- `@GetMapping(value = "/{id}", produces = MediaType.IMAGE_JPEG_VALUE)` for image file

- to add the type dynamically based on stored file content type :
    - store original file content type when saving or updating
    - when serving the file add the corresponding media type to the ResponseEntity based on some map or similar storage
        as demonstrated in FileUtil
      ```
      return ResponseEntity.ok()
                    .contentType(FileUtil.getMediaType(file.getType()))
                    .body(file.getData());
      ```
           

### FileController
- FileController exposes files Post, Get by id and Get with info endpoints, to demonstrate compressing and decompressing of files
- while the ImageController is restricted to image files, these endpoints are not restricted but to one that exists in the map 
  provided in FileUtil
- although the models of Image and File are a like, the decision was made to such design to be able to compare the saved space
 in database tables when compressed vs uncompressed, if same image file is saved in both tables.


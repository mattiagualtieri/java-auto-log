# 🪄 Java Auto Log

**AutoLog** is a lightweight Java/Spring library that provides **automatic method logging** via a simple annotation — `@AutoLog`.  
It logs input parameters, output values, and exceptions without requiring boilerplate logging code in every method.

---

## 📦 Installation

1. **Add the JitPack repository** to your project’s `pom.xml`:

```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>
```

2. **Add the AutoLog dependency**:

```xml
<dependency>
  <groupId>com.github.mattiagualtieri</groupId>
  <artifactId>java-auto-log</artifactId>
  <version>1.0.0</version> <!-- or the latest release -->
</dependency>
```

---

## 🧩 Usage Example

Annotate any method with `@AutoLog` to automatically log inputs and outputs:

```java
import com.guti.AutoLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BookService {

  private static final Logger logger = LoggerFactory.getLogger(BookService.class);

  @AutoLog
  public Book findBookById(Long id) {
    // your logic here
    return new Book(id, "The Art of Java");
  }
}
```

🧾 Example output in logs:
```
INFO  BookService - Called findBookById(42)
INFO  BookService - Call to findBookById returned Book[id=42, title=The Art of Java]
```

---

## 🧠 Advanced Usage — Custom Mappings

By default, `@AutoLog` uses each parameter’s `toString()` when logging inputs.  
You can customize this behavior per type using **mappings**.

Example:

```java
@AutoLog(
  mappings = {
    @AutoLog.Mapping(type = Book.class, method = "getTitle")
  }
)
public void processBook(Book book) {
  // ...
}
```

If `Book` is:
```java
public class Book {
  private final String title;
  public Book(String title) { this.title = title; }
  public String getTitle() { return title; }
}
```

Then the logs will include:
```
INFO  BookService - Called processBook(The Art of Java)
```
instead of the default `Book@ab23c1`.

---

## 🪪 License

MIT License © [Mattia Gualtieri](https://github.com/mattiagualtieri)

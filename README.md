# RAINTREE HOMEWORK

Task completed using Java and Maven.  
You can run compiled JAR from '\build' folder using command promt:  

```
java -jar NumbersGenerator.jar <fileName> <fileSizeMb> 
```

For example: 

```
java -jar NumbersGenerator.jar numbers.txt 10 
```
 
## TASK DESCRIPTION:
Write a JAVA program that will:

1.  Generate a file with random numeric (range from 1 to 2^64 - 1) data. File size is limited by command line options. The default file size limit is 64 MB. Each random number is separated by space (ASCII code 32). Program will require 1 argument, which is the file name to be generated.

2.  Read the file generated in step #1, analyze it and output it to the console. The output should include:  
    *  1. 10 most frequently appeared numbers in bar chart form.
    *  2. The count of Prime numbers.
    *  3. The count of Armstrong numbers.
    *  4. Output separately the time taken to read and analyze the file.    

Pay attention:

1.  Check error handling.
2.  Keep the code clean and formatted, follow the basic JAVA naming conventions.
3.  Program speed matters, you may use parallel processing.
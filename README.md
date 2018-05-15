# custom-date-object-reader

All the dates are of type Java 8 Instant

*  **dateReference** : this is a reference to at date object, this can come from the objectPropertyReader when the custom property is a date field
*  **timeZoneOut** : this is the time zone in which you want the output to be formatted in
*  **outputFormat** : this is the format to output the given time 

Sample Mapping
```
    <c:parameter>
        <c:name>MyNewDateString</c:name>
        <cm:dateObjectReader>
            <cm:dateReference>MyDateField</cm:dateReference>
            <cm:timeZoneOut>CST6CDT</cm:timeZoneOut>
            <cm:outputFormat>MM/dd/YYYY</cm:outputFormat>
        </cm:dateObjectReader>
    </c:parameter>
  

# Candidate Result Management System

1. Candidates can view their Result as well as print to pdf also.
2.Rank of all candidates can be viewed as list and also candidates can print this list as table in pdf format.
3.Admin can create Candidates Result and fill Candidate details.
4.New Admin can be created by entering ROOTPIN which is known only to other admin or software developer.(and it can be changed by configuration)
5.Admin can view their self Details as well rootpin.

### Project Language: java + JDBC + Maven+ .properties(external file for configuration)+.xlsx(external file for bult data input)+.xml(external file for logger configuration)
          Dependencies:
            1. mysql-connector-java
            2. lombok
            3. itextpdf
            4. ch.qos.logback(Slf4j)
            5.asciitable
            6. poi-ooxml
             (for more details please see pom.xml file.)
### mandatory requirements done :
-all credentials are  Base64 encrypted
- all configurations can be  changed through external properties file(No hardcoding)
-echo masking is done as **** in console or terminal during  typing any password or rootpin in runtime.
-file dialog opener is used so that user don't have to type any address in console.
-all mandatory comments is used for providing meanings to other developers.
-all exceptions and validations are handled accordingly.


package com.cx.prod.list.utils.csv.users;

import com.cx.prod.list.model.users.CsvColumnNames;
import com.cx.prod.list.model.users.User;
import com.cx.prod.list.utils.csv.CsvFileParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Set;

public class CsvUsersParser extends CsvFileParser<User> {

    private static final String[] dateFormatStrings = {"yyyy.MM.dd", "yyyy.MM.d", "yyyy.M.dd", "yyyy.M.d"};

    private Logger logger = LoggerFactory.getLogger(CsvUsersParser.class);

    private Validator validator;

    public CsvUsersParser(Reader ir) throws IOException {
        super(ir, CsvColumnNames.class);
        validator = getValidatorInstance();
    }

    protected Validator getValidatorInstance() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    protected User parse(CSVRecord record) {
        final int recordSize = record.size();
        String lastName = null, firstName = null, phoneNumber = null;
        LocalDate birthDate = null;
        if (recordSize > 0) {
            lastName = record.get(CsvColumnNames.LAST_NAME);
        }
        if (recordSize > 1) {
            firstName = record.get(CsvColumnNames.FIRST_NAME);
        }
        if (recordSize > 2) {
            birthDate = parseBirthDate(record);

        }
        if (recordSize > 3) {
            phoneNumber = parsePhoneNumber(record);
        }

        User user = new User(lastName, firstName, birthDate, phoneNumber);
        validateUser(user);
        return user;
    }

    private LocalDate parseBirthDate(CSVRecord record) {
        String birthDateStr = record.get(CsvColumnNames.BIRTH_DATE);
        LocalDate birthDate = parseDate(birthDateStr);
        return birthDate;
    }

    private String parsePhoneNumber(CSVRecord record) {
        String phoneNumber = record.get(CsvColumnNames.PHONE_NO);
        phoneNumber = phoneNumber.replaceAll("\\D+", "");
        if (phoneNumber.isEmpty()) phoneNumber = null;
        return phoneNumber;
    }

    private void validateUser(User user) {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (violations.size() > 0) {
            logger.debug("User" + user.toString() + " validation failed");
            throw new ConstraintViolationException("User not valid", violations);
        }
    }

    private LocalDate parseDate(String dateStr) {
        LocalDate date = null;
        if (dateStr != null) {
            dateStr = dateStr.replaceAll("[-./\\ ]+", ".");
            for (String dateFormatString : dateFormatStrings) {
                try {
                    date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(dateFormatString));
                    break;
                } catch (IllegalArgumentException | DateTimeParseException ex) {
                    logger.debug("Date string of " + dateStr + " not parsed properly using " + dateFormatString);
                }
            }
        }
        return date;
    }
}

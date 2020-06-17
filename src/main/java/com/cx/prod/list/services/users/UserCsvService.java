package com.cx.prod.list.services.users;

import com.cx.prod.list.model.users.User;
import com.cx.prod.list.utils.csv.users.CsvUsersParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserCsvService {

    private Logger logger = LoggerFactory.getLogger(UserCsvService.class);

    public List<User> parseCsvFile(Reader reader) throws IOException {
        CsvUsersParser csvFileParser = csvFileUtilFactory(reader);
        List<User> users = getUsers(csvFileParser);
        return users;
    }

    private List<User> getUsers(CsvUsersParser csvFileParser) {
        List<User> users = new ArrayList<>();

        User user = null;
        while (csvFileParser.hasNext()) {
            try {
                user = csvFileParser.process();
                users.add(user);
            } catch (ConstraintViolationException cve) {
                logger.error(cve.getMessage());
            }
        }
        return users;
    }

    protected CsvUsersParser csvFileUtilFactory(Reader reader) throws IOException {
        return new CsvUsersParser(reader);
    }

}

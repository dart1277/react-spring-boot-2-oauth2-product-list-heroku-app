package com.cx.prod.list.utils.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

public abstract class CsvFileParser<T> {
    private Iterable<CSVRecord> records;
    private Iterator<CSVRecord> recordIterator;

    public CsvFileParser(Reader ir, Class<? extends Enum<?>> clazz) throws IOException {
        this.records = CSVFormat.DEFAULT
                .withHeader(clazz)
                .withFirstRecordAsHeader()
                .withIgnoreEmptyLines()
                .withIgnoreHeaderCase()
                .withDelimiter(';')
                .withTrim()
                .parse(ir);
        this.recordIterator = records.iterator();
    }

    public T process() {
        if (recordIterator.hasNext()) {
            return parse(recordIterator.next());
        } else {
            return null;
        }

    }

    public void reset() {
        recordIterator = records.iterator();
    }

    public boolean hasNext() {
        return recordIterator.hasNext();
    }

    protected abstract T parse(CSVRecord record);
}

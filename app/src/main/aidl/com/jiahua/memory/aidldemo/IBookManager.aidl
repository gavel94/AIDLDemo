// IBookManager.aidl
package com.jiahua.memory.aidldemo;

// Declare any non-default types here with import statements
import com.jiahua.memory.aidldemo.moudle.Book;

interface IBookManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    List<Book> getBookList();

    void addBook(in Book book);
}

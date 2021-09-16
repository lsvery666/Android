// IBookManager.aidl
package com.bytedance.bindertest;

import com.bytedance.bindertest.Book;

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
}
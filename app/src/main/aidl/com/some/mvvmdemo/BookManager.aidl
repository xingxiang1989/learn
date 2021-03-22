package com.some.mvvmdemo;

import com.some.mvvmdemo.entity.Book;
import com.some.mvvmdemo.entity.Author;

interface BookManager{

    /**
     * 除了基本数据类型，其他类型的参数都需要标上方向类型：in(输入), out(输出), inout(输入输出)
     */
  void addBookIn(in Book book);

  void addBookOut(out Book book);

  void addBookInOut(inout Book book);

  List<Book> getBooks();

  void addAuthor(in Author author);

}

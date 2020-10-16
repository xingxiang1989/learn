package com.some.mvvmdemo;

import com.some.mvvmdemo.entity.Book;

interface BookManager{

    /**
     * 除了基本数据类型，其他类型的参数都需要标上方向类型：in(输入), out(输出), inout(输入输出)
     */
  void addBook(in Book book);

  List<Book> getBooks();

}

package com.readinglist.entity;

import com.readinglist.entity.api.ReadingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * "@Controller"注解，这样组件扫描会自动将其注册为
 * Spring应用程序上下文里的一个Bean。它还用了@RequestMapping注解，将其中所有的处理器
 * 方法都映射到了“/”这个URL路径上。
 */
@Controller
@RequestMapping("/Reading")
public class ReadingListController {
    private ReadingListRepository readingListRepository;

    @Autowired
    public ReadingListController(
            ReadingListRepository readingListRepository) {
        this.readingListRepository = readingListRepository;
    }

    @RequestMapping(value = "/{reader}", method = RequestMethod.GET)
    public String readersBooks(@PathVariable("reader") String reader, Model model) {
        //http://localhost:8080/Reading/123
        //List<Book> readingList = readingListRepository.findByReader(reader);
        List<Book> readingList = new ArrayList<>();
        for (int i = 0; i<5;i++){
            Book book = new Book();
            book.setReader(reader);
            readingList.add(book);
        }
        if (readingList != null) {
            model.addAttribute("books", readingList);
        }
        return "readingList";
    }

    @RequestMapping(value = "/{reader}", method = RequestMethod.POST)
    public String addToReadingList(
            @PathVariable("reader") String reader, Book book) {
        book.setReader(reader);
        readingListRepository.save(book);
        return "redirect:/{reader}";
    }
}

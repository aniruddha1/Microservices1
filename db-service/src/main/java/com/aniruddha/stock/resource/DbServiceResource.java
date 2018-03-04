package com.aniruddha.stock.resource;

import com.aniruddha.stock.model.Quote;
import com.aniruddha.stock.model.Quotes;
import com.aniruddha.stock.repository.QuotesRepository;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/db")
public class DbServiceResource {

	
    private QuotesRepository quotesRepository;

    public DbServiceResource(QuotesRepository quotesRepository) {
        this.quotesRepository = quotesRepository;
    }

    
    @GetMapping("/hello")
    public String greeting() {
        String text = "Hello Aniruddha";
        return text;
    }
    
    @GetMapping("/welcome")
    public String welcome(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "welcome";
    }
    
    @GetMapping("/{username}")
    public List<String> getQuotes(@PathVariable("username") final String username) {

        return getQuotesByUserName(username);
    }

    @PostMapping("/add")
    public List<String> add(@RequestBody final Quotes quotes) {

        quotes.getQuotes()
                .stream()
                .map(quote -> new Quote(quotes.getUserName(), quote))
                .forEach(quote -> quotesRepository.save(quote));
        return getQuotesByUserName(quotes.getUserName());
    }


    @PostMapping("/delete/{username}")
    public List<String> delete(@PathVariable("username") final String username) {

        List<Quote> quotes = quotesRepository.findByUserName(username);
        quotesRepository.delete(quotes);

        return getQuotesByUserName(username);
    }


    private List<String> getQuotesByUserName(@PathVariable("username") String username) {
        return quotesRepository.findByUserName(username)
                .stream()
                .map(Quote::getQuote)
                .collect(Collectors.toList());
    }



}
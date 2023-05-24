package com.example.springGilman.controller;

import com.example.springGilman.domain.Item;
import com.example.springGilman.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
/*이녀석은 컨트롤러다*/
@RequiredArgsConstructor
/*ItemService 타입의 itemService 생성자 만들어준다*/
@RequestMapping("items")
/*컨트롤러가 처리하는 기본 URL 경로 설정*/
public class ItemController {
   /* 왜 빨간줄이 뜨는거지?? autowire가 안된다
   * ItemService는 비즈니스로직 처리하는 서비스 클래스*/
    private final ItemService itemService;

    @GetMapping("new")
    /*아까 멤버 컨트롤러 있던 거랑 똑같다 새 아이템 생성함 */
    public String createForm(Model model) {
        model.addAttribute("itemForm", new Item());
        return "items/createItemForm";
    }

    @PostMapping("new")
    /*마찬가지로, POST 요청 들어오면 */
    public String create(Item item) {
        itemService.save(item);
        return "redirect:/";
    }

    @GetMapping("")
    /*생성한 아이템 전체 조회, 그러고보니 이녀석은 조회라 PostMapping이 필요 없나보다*/
    public String findAll(Model model) {
        List<Item> itemList = itemService.findAll();
        model.addAttribute("itemList", itemList);
        return "items/itemList";
    }

    @GetMapping("{id}/update")
    /* 아이템 수정 */
    public String updateForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("itemFormUpdate", itemService.findById(id));
        return "items/updateItemForm";
    }

    @PostMapping("update")
    public String update(Item item) {
        itemService.update(item.getId(), item);
        return "redirect:/items";
    }
    /*생각해보니 작동시키려면 ViewController도 있어야 하는거 같다*/

}


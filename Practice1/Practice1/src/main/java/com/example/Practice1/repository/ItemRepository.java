package com.example.Practice1.repository;

import com.example.Practice1.domain.Item;


// 어째서... JPARepo 들여오고 싶은데 springframework 아래 data 폴더가 없음
public interface ItemRepository extends JpaRepository<Item, Long> {
}

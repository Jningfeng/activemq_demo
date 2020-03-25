package com.jnf.activemq.Thread;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
class User{

    private Integer id ;
    private String userName ;
    private int age ;

}
/*
* 按照给出数据，找出同时满足以下条件的用户，也即以下条件全部满足
* 偶数Id 且年龄大于24 且用户名转为大写 且用户名字母倒排序 只输出一个用户名
* */
public class StreamDemo {
    public static void main(String[] args) {
        User user1 = new User(11,"a",23);
        User user2 = new User(12,"b",24);
        User user3 = new User(13,"c",22);
        User user4 = new User(14,"d",28);
        User user5 = new User(16,"e",26);

        List<User> list = Arrays.asList(user1,user2,user3,user4,user5);

         list.stream().filter(u -> {
              return  u.getId() % 2 == 0 ;
         }).filter(u -> {
              return u.getAge() > 24;
         }).map(m -> {
              return m.getUserName().toUpperCase();
         }).sorted((o1,o2) -> {
              return o2.compareTo(o1);
         }).limit(1).forEach(System.out::println);




//四大函数式接口
      //函数型接口
       /* Function<String,Integer> function = (String s) -> {return s.length();};
        System.out.println(function.apply("abc"));

      //断定型接口
        Predicate<String> predicate = (String s) -> {return s.isEmpty();};
        System.out.println(predicate.test("a"));

     //消费型接口
        Consumer<String> consumer = (String s) -> {System.out.println(s);};
        consumer.accept("aaa");

     //供给型接口
        Supplier<String>  supplier = () -> {return "Java";};
        System.out.println(supplier.get());*/
    }
}

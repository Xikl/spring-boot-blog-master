# spring-boot-blog-master
spring-boot-blog-master
>源于毕设 
```
你好，明天！今天已经过去，不知道你的任务是否完成了呢？
```
>学习心得
#### 1. spring data jpa中getOne 和 findById的区别
```
- getOne 返回的是一个实体的代理对象
- findById 返回的是实体，官方推荐使用这个
@@具体还有待测试
```
#### 2. mysql中Timestamp，datetime 区别
```
Timestamp 时间戳。范围是’1970-01-01 00:00:00’到2037年
datetime 支持的范围是’1000-01-01 00:00:00’到’9999-12-31 23:59:59’
```
#### 3. list的删除思考
```
- java8中快速利用迭代器删除：
otes.removeIf(vote -> vote.getId().equals(voteId));
```
#### 4. spring security中principle的思考
```
阅读源码发现：
- principle在认证成功后会从一个cache中根据用户名拿到用户信息
然后将其塞进一个xxxxToken类中如UsernamePasswordAuthenticationToken
所以我们就可以在全局中拿到这个用户信息
```
#### 5. spring data es 和 ElasticsearchTemplate的区别
```
todo
```
#### 6.前端底部导航置底
```css
/* Footer */
.blog-footer {
    position: fixed; /* 底部置底*/
    bottom: 0;
    width: 100%;
    height: 60px;
    line-height: 60px; /* Vertically center the text there */
}
```
#### 7.controller中重定向的问题
- 当使用原生 "redirect:/u/" + username + "/blogs" 时，username
为中文的时候回乱码。
- 解决办法是采用一个RedirectAttributes类来进行填参操作
```
@GetMapping("/{username}")
public String userSpace(@PathVariable("username") String username, Model model, RedirectAttributes redirectAttrs) {
    User user = (User) userDetailsService.loadUserByUsername(username);
    model.addAttribute("user", user);
    redirectAttrs.addAttribute("username", username);
    return "redirect:/u/{username}/blogs";
}
```
#### 8.Elasticsearch 5.x 中fielddata 默认为false的问题
- 5.x中Text field使用fielddata的这种内存数据结构。它会在内存中存储反转整
个索引的每个片段，包括文档关系。因为它非常耗费内存所以默认是关闭的disabled，
一般不必要设置的不要设置。 
- 打开方法：
```
PUT my_index/_mapping/_doc
{
  "properties": {
    "my_field": { 
      "type":     "text",
      "fielddata": true
    }
  }
}
```
- 除了这种方法，还可以加上spring data elasticsearch的注解@Field(fielddata=true)
#### 9.spring data jpa 中懒加载的问题
##### 9.1问题分析
 + 基于对Hibernate和JPA的理解，在ORM中，其为了提升性能使用了Lazy加载，就是在使用的时候，
 才会加载额外的数据，故导致了在使用之时再加载数据之时， session失效的问题出现。所以问题的目标点实现提前加载数据。
##### 9.2解决办法
 + 尝试1：  在Service方法中新增了@Transactional进行事务添加
   结果1：  无效
 + 尝试2： 在@OneToMany的方法上，使用@Lazy(false)
   结果2：  无效
 + 尝试3： 在@OneToMany的参数中使用fetch=FetchType=Eager
   结果3:   问题解决
```
@OneToMany(fetch=FetchType.EAGER)  
@JoinColumn(name="category_id",referencedColumnName="id")  
private List<MealDish> dishes;  
```
 + 尝试4： 在application.properties的配置文件中新增spring.jpa.open-in-view=true
   结果4： 问题解决
```
spring.jpa.open-in-view=true
or 
spring:
  jpa:
    open-in-view: true
```
##### 10.elasticsearch中注解@Field的使用
- fieldType
 + 5.x中没有string类型，只能用text 和 keyword 来来表示文本
 + 如果fieldType中有一个设为auto类型，那么其他指定了的都会失效
 + fielddata=true 只能设置到text类型
 
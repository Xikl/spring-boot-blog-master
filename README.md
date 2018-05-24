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
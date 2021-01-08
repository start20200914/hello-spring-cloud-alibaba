package com.zlm.hello.spring.cloud.alibaba.nacos.provider.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Data
public class User implements Serializable {



    private static final long serialVersionUID = -1914801793478594984L;

    @ApiModelProperty("用户ID")
    private Integer id;

    @NotBlank(message = "请填写名字")
    @Length(max = 100, message = "用户名字，请重新填写")
    @ApiModelProperty("用户名字")
    private String name;

    @NotBlank(message = "请填写密码")
    @Length(max = 100, message = "用户名字，请重新填写")
    @Pattern(regexp = "^[a-zA-Z]\\w{5,17}$", message = "以字母开头，长度在6~18之间，只能包含字母、数字和下划线")
    @ApiModelProperty("用户密码")
    private String password;

    private Integer age;

    public User(Integer id, @NotBlank(message = "请填写名字") @Length(max = 100, message = "用户名字，请重新填写") String name, @NotBlank(message = "请填写密码") @Length(max = 100, message = "用户名字，请重新填写") @Pattern(regexp = "^[a-zA-Z]\\w{5,17}$", message = "以字母开头，长度在6~18之间，只能包含字母、数字和下划线") String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public User(Integer id, @NotBlank(message = "请填写名字") @Length(max = 100, message = "用户名字，请重新填写") String name, @NotBlank(message = "请填写密码") @Length(max = 100, message = "用户名字，请重新填写") @Pattern(regexp = "^[a-zA-Z]\\w{5,17}$", message = "以字母开头，长度在6~18之间，只能包含字母、数字和下划线") String password, Integer age) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.age = age;
    }

    public User() {
    }

    // 多线程的future接收返回值。
    //每一个请求对象中都有一个future接收请求。
    private CompletableFuture<User> future;


}

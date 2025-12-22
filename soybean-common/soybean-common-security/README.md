# Soybean Common Security

基于Sa-Token的安全认证模块，提供用户认证、授权、权限控制等功能。

## 功能特性

- 基于Sa-Token实现用户认证和授权
- 支持多种用户类型（管理员、普通用户等）
- 支持基于注解的权限控制
- 支持操作日志记录
- 支持Token管理（刷新、踢出等）
- 提供统一的安全工具类

## 使用方法

### 1. 引入依赖

```xml
<dependency>
    <groupId>com.soybean</groupId>
    <artifactId>soybean-common-security</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 2. 配置文件

在application.yml中添加以下配置：

```yaml
# Sa-Token配置
sa-token:
  # token名称 (同时也是cookie名称)
  token-name: satoken
  # token有效期，单位s 默认30天, -1代表永不过期
  timeout: 2592000
  # token临时有效期 (指定时间内无操作就视为token过期) 单位: 秒
  active-timeout: -1
  # 是否允许同一账号并发登录 (为true时允许一起登录, 为false时新登录挤掉旧登录)
  concurrent-login: true
  # 在多人登录同一账号时，是否共用一个token (为true时所有登录共用一个token, 为false时每次登录新建一个token)
  share-login: false
  # token风格
  style: uuid

# 自定义安全配置
soybean:
  security:
    enabled: true
    token:
      name: satoken
      timeout: 2592000
      active-timeout: -1
      concurrent-login: true
      share-login: false
      style: uuid
    login:
      single-device: false
      password-encoder: bcrypt
      max-retry-count: 5
      lock-time: 30
      log-login: true
```

### 3. 使用示例

#### 3.1 用户登录

```java
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Result<LoginResult> login(@RequestBody LoginDTO loginDTO) {
        LoginResult result = authService.login(
            loginDTO.getUsername(), 
            loginDTO.getPassword(), 
            UserTypeEnum.valueOf(loginDTO.getUserType()),
            loginDTO.isRemember()
        );
        return Result.success(result);
    }

    @PostMapping("/logout")
    public Result<Void> logout() {
        authService.logout();
        return Result.success();
    }
}
```

#### 3.2 使用注解进行权限控制

```java
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/profile")
    @RequireLogin
    public Result<LoginUser> getProfile() {
        LoginUser user = SecurityUtil.getCurrentUser();
        return Result.success(user);
    }

    @PostMapping("/create")
    @RequireLogin
    @RequirePermission(value = {"user:create"}, operation = "创建用户", module = "用户管理")
    public Result<Void> createUser(@RequestBody UserCreateDTO dto) {
        // 创建用户逻辑
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @RequireLogin
    @RequireRole("admin")
    public Result<Void> deleteUser(@PathVariable String id) {
        // 删除用户逻辑
        return Result.success();
    }
}
```

#### 3.3 使用工具类

```java
@Service
public class UserService {

    public void doSomething() {
        // 获取当前用户ID
        String userId = SecurityUtil.getUserId();

        // 获取当前用户信息
        LoginUser user = SecurityUtil.getCurrentUser();

        // 检查权限
        if (SecurityUtil.hasPermission("user:create")) {
            // 有权限
        }

        // 检查角色
        if (SecurityUtil.hasRole("admin")) {
            // 是管理员
        }
    }
}
```

## 核心类说明

- `AuthService`: 认证服务接口，提供登录、退出、权限检查等方法
- `SecurityUtil`: 安全工具类，提供便捷的安全操作方法
- `LoginUser`: 登录用户模型
- `@RequireLogin`: 登录校验注解
- `@RequirePermission`: 权限校验注解
- `@RequireRole`: 角色校验注解
- `SecurityExceptionHandler`: 安全异常处理器

## 注意事项

1. 使用本模块前需要确保Redis已正确配置并启动
2. 实际项目中需要实现自己的用户认证逻辑，替换AuthServiceImpl中的示例代码
3. 自定义注解可以记录操作日志，需要根据实际需求实现日志服务

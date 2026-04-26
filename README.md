# eCNY
数字人民币系统建设

## 当前实现（Spring Boot）
已实现一个可运行的基础数字人民币钱包后端（MVP）：

- 钱包开户
- 查询钱包余额
- 充值
- 钱包间转账
- 查询交易流水（最近 50 条）

## 技术栈
- Java 17
- Spring Boot 3
- Spring Web / Validation / Spring Data JPA
- H2 内存数据库

## 启动方式
```bash
mvn spring-boot:run
```

## 核心接口
基础路径：`/api/v1/wallets`

1. 开户 `POST /api/v1/wallets`
```json
{
  "walletNo": "W10001",
  "ownerName": "Alice"
}
```

2. 钱包查询 `GET /api/v1/wallets/{walletNo}`

3. 充值 `POST /api/v1/wallets/{walletNo}/deposit`
```json
{
  "amount": 100.00
}
```

4. 转账 `POST /api/v1/wallets/transfer`
```json
{
  "fromWalletNo": "W10001",
  "toWalletNo": "W10002",
  "amount": 35.50,
  "note": "午餐AA"
}
```

5. 交易流水 `GET /api/v1/wallets/{walletNo}/transactions`

## 说明
你提供的 plan 链接为 ChatGPT share URL，但在当前执行环境中返回 `share_not_found`，无法直接读取具体计划内容。
本次先按“数字人民币钱包核心能力”完成了可运行后端骨架与关键交易闭环。若你粘贴 plan 详细条目，我可以继续逐条对齐实现。

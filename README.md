# LittleUA

## DB init
1. `cd docker/`
2. `docker compose up`

## APIs

| Method | Endpoint        | Description                                                                         | Return example                                                                                                                                                                              |
|--------|-----------------|-------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| GET    | /headers/mirror | Mirrors the client's headers                                                        | `[{"name": "user-agent","value": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36"},{"name": "accept","value": "*/*"}, ...]`| 
| POST   | /useragents     | Saves or updates the client's useragent                                             | `Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36`                                                                     |

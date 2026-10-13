# Session 11 - Bai 3: Redis Pub/Sub

Chay `docker compose up -d --wait` va `./gradlew :pharmacy-service:bootRun`.

`POST /api/imports` voi JSON `{"medicineName":"Panadol","quantity":100}` cong ton kho va luu phieu nhap vao PostgreSQL truoc, sau do publish JSON len channel `pharmacy-alerts`. Subscriber trong service in thong bao ra console. Co the chay them instance voi `SERVER_PORT=8091` de thay fan-out. Redis Pub/Sub khong luu message khi subscriber offline.

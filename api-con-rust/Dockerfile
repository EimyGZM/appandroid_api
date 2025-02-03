FROM rust:1.74 as builder
WORKDIR /app
COPY . .
RUN cargo build --release

FROM debian:buster-slim
WORKDIR /app
COPY --from=builder /app/target/release/race_tracker_api .
EXPOSE 8080
CMD ["./race_tracker_api"]

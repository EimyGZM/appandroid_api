use reqwest::Client;
use serde_json::json;

pub async fn send_push_notification(token: &str, message: &str) {
    let client = Client::new();
    let payload = json!({
        "to": token,
        "notification": {
            "title": "Tarea Completada",
            "body": message
        }
    });

    let fcm_server_key = "EimyGuzm@n";
    let _ = client.post("https://fcm.googleapis.com/fcm/send")
        .header("Authorization", format!("key={}", fcm_server_key))
        .header("Content-Type", "application/json")
        .json(&payload)
        .send()
        .await;
}
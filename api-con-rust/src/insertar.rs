use actix_web::{post, web, HttpResponse, Responder};
use sqlx::MySqlPool;
use serde::Deserialize;

#[derive(Deserialize)]
pub struct NewUser {
    nombre: String,
    email: String,
    telefono: String,
    pass: String,
}

#[post("/insertar_usuario")]
pub async fn insertar_usuario(
    pool: web::Data<MySqlPool>,
    user: web::Json<NewUser>,
) -> impl Responder {
    let query = sqlx::query!(
        "INSERT INTO usuario (nombre, email, telefono, pass) VALUES (?, ?, ?, ?)",
        user.nombre,
        user.email,
        user.telefono,
        user.pass
    )
    .execute(pool.get_ref())
    .await;

    match query {
        Ok(_) => HttpResponse::Ok().body("EL USUARIO SE INSERTO DE FORMA EXITOSA"),
        Err(_) => HttpResponse::InternalServerError().body("ERROR AL INSERTAR EL USUARIO"),
    }
}

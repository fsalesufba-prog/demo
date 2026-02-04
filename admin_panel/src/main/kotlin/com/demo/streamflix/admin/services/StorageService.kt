package com.demo.streamflix.admin.services

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.await
import kotlinx.coroutines.launch

class StorageService {
    private val scope = MainScope()
    private val client = HttpClient()
    
    suspend fun uploadFile(file: dynamic, endpoint: String = "upload"): String? {
        return try {
            val formData = FormData.create()
            formData.append("file", file)
            
            val response: HttpResponse = client.post("${Constants.API_BASE_URL}/api/$endpoint") {
                bearerAuth(AuthService.getToken() ?: "")
                setBody(formData)
                headers {
                    append(HttpHeaders.ContentType, ContentType.MultiPart.FormData.toString())
                }
            }
            
            if (response.status == HttpStatusCode.OK) {
                val result: Map<String, String> = response.body()
                result["url"]
            } else {
                null
            }
        } catch (e: Exception) {
            console.error("Upload error:", e)
            null
        }
    }
    
    suspend fun deleteFile(url: String): Boolean {
        return try {
            val response: HttpResponse = client.delete("${Constants.API_BASE_URL}/api/upload") {
                bearerAuth(AuthService.getToken() ?: "")
                parameter("url", url)
            }
            
            response.status == HttpStatusCode.OK
        } catch (e: Exception) {
            console.error("Delete file error:", e)
            false
        }
    }
    
    fun close() {
        client.close()
    }
}
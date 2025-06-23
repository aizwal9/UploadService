output "cluster_name" {
  value = google_container_cluster.gke_cluster.name
}

output "bucket_url" {
  value = google_storage_bucket.doc_bucket.url
}

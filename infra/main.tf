provider "google" {
  credentials = var.gcp_credentials
  project     = var.project_id
  region      = var.region
}

resource "google_artifact_registry_repository" "docker_repo" {
  location      = var.region
  repository_id = var.artifact_repo_name
  description   = "Artifact registry for doc pipeline"
  format        = "DOCKER"

  lifecycle {
    prevent_destroy = true
    ignore_changes  = [labels, description]
  }
}

resource "google_container_cluster" "gke_cluster" {
  name               = var.cluster_name
  location           = var.zone
  initial_node_count = 2

  node_config {
    machine_type = "e2-medium"
    oauth_scopes = [
      "https://www.googleapis.com/auth/cloud-platform"
    ]
  }

  lifecycle {
    ignore_changes = [initial_node_count]
  }
}

resource "google_storage_bucket" "doc_bucket" {
  name     = var.bucket_name
  location = var.region
  force_destroy = true

  lifecycle {
    prevent_destroy = true
  }
}

resource "google_pubsub_topic" "upload_topic" {
  name = var.topic_name

  lifecycle {
    prevent_destroy = true
  }
}

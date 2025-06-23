#!/bin/bash

# 🚨 Update these before running
PROJECT_ID="microservices-786"
REGION="asia-south1"
ZONE="asia-south1-a"
ARTIFACT_REPO_NAME="doc-registry"
BUCKET_NAME="doc-upload-bucket-unique"
TOPIC_NAME="document-processing-topic"
CLUSTER_NAME="document-cluster"

echo "🔁 Importing Artifact Registry..."
terraform import \
  google_artifact_registry_repository.docker_repo \
  projects/${PROJECT_ID}/locations/${REGION}/repositories/${ARTIFACT_REPO_NAME}

echo "🔁 Importing GKE Cluster..."
terraform import \
  google_container_cluster.gke_cluster \
  projects/${PROJECT_ID}/locations/${ZONE}/clusters/${CLUSTER_NAME}

echo "🔁 Importing GCS Bucket..."
terraform import \
  google_storage_bucket.doc_bucket \
  ${BUCKET_NAME}

echo "🔁 Importing Pub/Sub Topic..."
terraform import \
  google_pubsub_topic.upload_topic \
  projects/${PROJECT_ID}/topics/${TOPIC_NAME}

echo "✅ All resources imported successfully!"

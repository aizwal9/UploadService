variable "project_id" {}
variable "region" { default = "asia-south1" }
variable "zone"   { default = "asia-south1-a" }
variable "cluster_name" { default = "document-cluster" }
variable "bucket_name" { default = "doc-upload-bucket-unique" }
variable "topic_name"  { default = "document-upload-topic" }
variable "artifact_repo_name" { default = "doc-registry" }
variable "gcp_credentials" {
  description = "Service account credentials JSON"
  type        = string
}

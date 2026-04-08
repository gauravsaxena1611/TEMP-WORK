# Deployment Diagram Specification
## Reference for generating-architecture-diagrams skill

Based on UML 2.5.1 deployment diagram notation and cloud architecture
diagramming conventions. Shows where software artifacts run on infrastructure.

---

## 1. Purpose

Shows the physical or virtual infrastructure on which software artifacts
are deployed. Answers: "Where does each component run, and how are the
infrastructure nodes connected?"

### Audience
DevOps engineers, infrastructure architects, platform teams, SREs.

### When to Use Over Other Types
- When documenting cloud or on-premises hosting topology
- When planning infrastructure changes or migrations
- When explaining the deployment architecture for ops/SRE teams
- When documenting network zones, load balancers, and scaling patterns

---

## 2. Element Styles

### Infrastructure Node (Server/VM/Container Host)
- Shape: 3D box (cube perspective) or rectangle with deployment icon
- Fill: #E8EAF6, Border: #5C6BC0
- Label: Node name (bold, 13px), node type and specs below (10px)
- Examples: `Web Server (EC2 t3.large)`, `K8s Worker Node`, `OpenShift Pod`

```
draw.io style: shape=mxgraph.aws4.resourceIcon;resIcon=mxgraph.aws4.ec2;...
```

OR generic box:
```
draw.io style: rounded=1;whiteSpace=wrap;html=1;fillColor=#E8EAF6;strokeColor=#5C6BC0;arcSize=8;
```

### Execution Environment (Container/Runtime)
- Shape: Rectangle with dashed border inside a node
- Fill: #F3E5F5, Border: #AB47BC, dashed
- Label: Runtime name (bold), technology (e.g., "JVM 17", "Node.js 20", "Docker")

### Artifact (Deployed Software)
- Shape: Rectangle with artifact icon (document fold)
- Fill: #E8F5E9, Border: #66BB6A
- Label: Artifact name (bold), version, technology
- Examples: `backend-api.jar v2.1`, `frontend-spa (React 18)`, `batch-job.py`

### Database Node
- Shape: Cylinder
- Fill: #FFF3E0, Border: #FF9800
- Label: DB name (bold), technology, size/tier
- Examples: `PostgreSQL 15 (RDS db.r5.2xlarge)`, `Redis Cluster 7.0`

### Load Balancer
- Shape: Horizontal bar or specific cloud icon
- Fill: #E3F2FD, Border: #1E88E5
- Label: LB name, type (ALB, NLB, HAProxy)

### Network Zone / VPC / Subnet
- Shape: Large dashed rectangle containing nodes
- Fill: transparent or very light (#FAFAFA), Border: #BDBDBD, dashed
- Label: Zone name at top-left (e.g., "VPC: prod-vpc", "DMZ", "Private Subnet")

### Cloud Region / Availability Zone
- Shape: Large rounded rectangle
- Fill: transparent, Border: #90A4AE
- Label: Region/AZ name (e.g., "us-east-1a", "Azure East US")

### Firewall / WAF
- Shape: Shield icon or rectangle with firewall label
- Fill: #FFCDD2, Border: #E53935
- Label: "WAF", "Firewall", security group name

---

## 3. Connection Styles

| Connection Type | Style | Color | Usage |
|----------------|-------|-------|-------|
| Network connection | Solid line | #616161 | Standard network link |
| HTTPS / TLS | Solid with lock icon label | #1B5E20 | Encrypted communication |
| Internal network | Solid thin | #9E9E9E | Within same VPC/zone |
| Cross-zone / VPN | Dashed | #FF6F00 | Between zones or VPN |
| Deployment | Dotted with arrow | #1565C0 | Artifact → Node (deploys to) |

### Connection Labels
- Protocol and port: `HTTPS:443`, `TCP:5432`, `gRPC:50051`
- Bandwidth or latency constraints (if known)

---

## 4. Cloud Provider Icon Sets

draw.io includes official cloud icon libraries:
- **AWS**: `mxgraph.aws4.*` shapes (EC2, RDS, S3, Lambda, ECS, EKS, ALB)
- **Azure**: `mxgraph.azure.*` shapes (VM, SQL, Blob, Functions, AKS)
- **GCP**: `mxgraph.gcp2.*` shapes (GCE, Cloud SQL, GCS, Cloud Functions, GKE)
- **Kubernetes**: `mxgraph.kubernetes.*` shapes (Pod, Service, Ingress, Deployment)

Use cloud-specific icons when the user specifies a cloud provider.
Use generic shapes when the deployment is cloud-agnostic or unspecified.

---

## 5. Layout Guidelines

- Network zones as outer containers (VPC → Subnet → Node)
- Load balancers at zone boundaries
- Firewalls/WAFs at security perimeters
- Databases in data tier (bottom or right)
- External users/clients at top or left
- Flow generally top-to-bottom or left-to-right

---

## 6. Sizing Guidelines

| Element | Recommended Size |
|---------|-----------------|
| Canvas | 3000x2000 minimum |
| Infrastructure node | 180x100px |
| Execution environment | 150x80px (inside node) |
| Artifact | 120x50px (inside environment) |
| Database cylinder | 140x90px |
| Network zone container | Varies — contains child nodes |
| Load balancer | 120x40px |
| Font: node name | 13px bold |
| Font: specs/details | 10px regular |

---

## 7. Deployment Diagram Quality Checklist

```
☐ All software artifacts mapped to infrastructure nodes
☐ Network zones clearly labeled (VPC, subnet, DMZ)
☐ Firewall/WAF shown at security boundaries
☐ Load balancers positioned correctly
☐ All connections labeled with protocol and port
☐ Cloud provider icons used when provider is specified
☐ Scaling notation shown (instance count, auto-scaling group)
☐ Database nodes show technology and tier
☐ No assumed infrastructure — all from sources
☐ TBD markers on nodes with unknown specifications
☐ Diagram title includes environment name (prod, staging, dev)
```

---

## 8. Sources

| Source | Relevance |
|--------|-----------|
| UML 2.5.1 (OMG) | Deployment diagram notation standard |
| AWS Architecture Icons (2024) | Official AWS diagram shapes |
| Azure Architecture Icons | Official Azure diagram shapes |
| draw.io cloud shape libraries | Cloud provider shape implementations |
| C4 Model Deployment Diagram | Supplementary deployment view guidance |

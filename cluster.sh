minikube start -n 3 --driver=docker --cpus=1 --memory=8192 --addons=metrics-server --addons=metallb

minikube addons enable metrics-server
minikube addons enable metallb
minikube ip
minikube addons configure metallb

kubectl cluster-info
kubectl api-resources |grep gateway

kubectl run -it --rm dns-test --image=giantswarm/tiny-tools
kubectl get crd gateways.gateway.networking.k8s.io
# kubectl apply -k github.com/kubernetes-sigs/gateway-api/config/crd/experimental
kubectl apply -k github.com/kubernetes-sigs/gateway-api/config/crd/standard
# kubectl kustomize "github.com/kubernetes-sigs/gateway-api/config/crd" | kubectl apply -f -


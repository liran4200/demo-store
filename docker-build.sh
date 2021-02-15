TAG=$1
docker build -t "${TAG}" .


# run docker run -p 8080:8080 ${TAG}
# create deployment
kubectl create --kubeconfig=config-demo -f Dev/demo/mainfest.yml

# get pods names
# kubectl get --kubeconfig=config-demo pods

# logs
# kubectl logs --kubeconfig=config-demo demo-store-55647b7554-cgbll
kubectl describe --kubeconfig=config-demo pods demo-store-55647b7554-cgbll

# kubectl apply --kubeconfig=config-demo -f Dev/demo/mainfest.yaml

If you see a FailedScheduling [...] 0/n nodes are available warning
mentioned, you have run out of nodes available to assign this pod to.

  Warning  FailedScheduling  3m (x57 over 19m)  default-scheduler  0/1 nodes are available: 1 MatchNodeSelector.

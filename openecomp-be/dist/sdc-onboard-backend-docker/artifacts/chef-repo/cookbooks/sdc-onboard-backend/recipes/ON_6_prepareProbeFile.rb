template "/var/lib/ready-probe.sh" do
  source "ready-probe.sh.erb"
  sensitive true
  mode 0755
  variables({
        :onboard_port           => "#{node['ONBOARDING_BE'][:http_port]}",
        :ssl_port               => "#{node['ONBOARDING_BE'][:https_port]}"
     })
end

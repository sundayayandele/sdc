template "/tmp/create_cassandra_user.sh" do
  source "create_cassandra_user.sh.erb"
  sensitive true
  mode 0755
  variables({
     :cassandra_ip => "HOSTIP",
     :cassandra_pwd => node['cassandra'][:cassandra_password],
     :cassandra_usr => node['cassandra'][:cassandra_user]
  })
end


bash "create-sdc-user" do
   code <<-EOH
     cd /tmp ; /tmp/create_cassandra_user.sh
   EOH
end

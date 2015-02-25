www.json.org
# cpc-documentary
https://www.fluidui.com/
ngc0316zyh@hotmail.com
Password:Team12


package diagram相当于把整个系统分为若干个文件夹，每个文件夹中存储一种类，文件夹之间互相有访问限制和联系

User Show Layer --- User Interface   接受客户请求，返回相关数据，提供访问程序的渠道
Data Access Layer  访问和操作原始数据，只针对数据文件
Entity 存储数据？数据类型？
Business Logic Layer --- Domain	对具体问题的操作和逻辑处理，操作数据层，把数据层中的小操作进行组合

UI->BLL->DAL DAL只被BLL访问 BLL只能被UI访问
UI/BLL/DAL -> Entity

如果增加一个IDAL，可以用工厂模式，即UI->BLL BLL->Factory/IDAL DAL->IDAL Factory->IDAL
Factory负责提取IDAL的产品，决定BLL需要哪一个，BLL直接从Factory中获得对象，DAL保持独立，更改的是IDAL中的内容

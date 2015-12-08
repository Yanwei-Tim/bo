<%@ page import="com.bcgogo.config.ConfigController" %>
<%--
  Created by IntelliJ IDEA.
  User: MZDong
  Date: 11-12-22
  Time: 下午6:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ include file="/WEB-INF/views/includes.jsp" %>--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>注册_经营信息</title>

    <link rel="stylesheet" type="text/css" href="styles/style<%=ConfigController.getBuildVersion()%>.css"/>
    <script type="text/javascript" src="js/extension/jquery/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="js/base<%=ConfigController.getBuildVersion()%>.js"></script>
    <script type="text/javascript" src="js/application<%=ConfigController.getBuildVersion()%>.js"></script>

    <script type="text/javascript">
        $().ready(function () {
            $("#div_close")[0].onclick = function () {
                window.open('', '_parent', '');
                window.close();
            }
        });
    </script>

</head>
<body class="bodyMain">
<div class="register_agreement">
    <div class="areementTitle">一发软件与统购车业网用户注册、使用协议</div>
    <br/><br/>

    <div class="agreementContent">
        　　本协议是您作为用户与苏州统购信息科技有限公司（简称“本公司”）之间就一发软件与统购车业网站（下称“本网站”，网址:<a
            href="http://www.bcgogo.com">http://www.bcgogo.com</a>）注册、使用等相关事宜所订立的合约请您仔细阅读本协议,您点击“同意以下协议，提交”按钮后，本协议即构成对双方有约束力的法律文件。
    </div>
    <br/>

    <div class="areementList">
        　　第一条 本协议服务条款的确认和接纳
    </div>
    <div class="agreementContent">
        　　一发软件由本公司开发，其著作权归属本公司。本网站的各项电子服务的所有权和运作权归本公司所有。用户同意所有注册协议条款并完成注册程序，才能成为一发软件与本网站的正式用户。用户确认：本协议条款是处理双方权利义务的契约，始终有效，双方另有特别约定的，依其规定。
    </div>
    <br/>

    <div class="areementList">
        　　第二条 使用基本条件
    </div>
    <div class="agreementContent">
        　　一发软件与本网站须通过互联网实现其使用功能。用户必须自行（1）提供设备，如电脑或者其他上网终端、调制解调器及其他上网装置； （2）上网并支付与此服务有关的如网络接入费等费用。
    </div>
    <br/>

    <div class="areementList">
        　　第三条 软件更新
    </div>
    <div class="agreementContent">
        　　本公司对一发软件提供免费在线升级服务，用户同意该项服务由本公司自主进行，本公司无需另行通知用户即可完成。
    </div>
    <br/>

    <div class="areementList">
        　　第四条 用户言行承诺用户独立承担其发布内容的责任。用户承诺：
    </div>
    <div class="agreementContent">
        　　（1）不得传输或发表如下内容之言论/信息：违反宪法确定的基本原则的；煽动抗拒、破坏宪法和法律、行政法规实施的，煽动颠覆国家政权，推翻社会主义制度的，煽动分裂国家、破坏国家统一的的，煽动民族仇恨、民族歧视、破坏民族团结的；以非法组织名义活动的；损害国家社会公共利益和涉及国家安全的；任何违法犯罪的、骚扰性的、中伤他人的、辱骂性的、恐吓性的、伤害性的、庸俗的，淫秽的、不文明的等信息资料；煽动非法集会、结社、游行、示威、聚众扰乱社会秩序的。<br/>
        　　（2）用户不得利用本网站从事以下活动：<br/> 　　 (a) 未经允许，进入计算机信息网络或者使用计算机信息网络资源的；<br/> 　　 (b) 未经允许，对计算机信息网络功能进行删除、修改或者增加的；<br/> 　　
        (c) 未经允许，对进入计算机信息网络中存储、处理或者传输的数据和应用程序进行删除、修改或者增加的；<br/> 　　 (d) 故意制作、传播计算机病毒等破坏性程序的；<br/> 　　 (e)
        其他危害计算机信息网络安全的行为。<br/> 　　 （3）用户不得以任何方式干扰本网站的服务。<br/> 　　 （4）不得利用本网站从事洗钱、窃取商业秘密、窃取个人信息等违法犯罪活动；<br/> 　　
        （5）不得发布任何侵犯他人著作权、商标权等知识产权或合法权利的内容；<br/>
        　　 （6）不得教唆他人从事本条所禁止的行为；<br/> 　　 （7）用户在本网站上发布信息或者利用本网站的服务时还必须符合其他有关国家和地区的法律规定以及国际法的有关规定。<br/> 　　
        （8）用户应遵守本网站的所有其他规定和程序。用户应不时关注并遵守本公司、本网站不时公布或修改的各类合法规则规定。本网站保有删除本网站内各类不符合法律政策或不真实的信息内容而无须通知用户的权利。若用户未遵守以上规定的，本公司/本网站有权作出独立判断并采取暂停或关闭用户帐号等措施。用户须对自己在网上的言论和行为承担法律责任。
    </div>
    <br/>

    <div class="areementList">
        　　第五条 用户信息
    </div>
    <div class="agreementContent">
        　　5.1用户应自行诚信依本公司/本网站要求提供注册资料，用户同意其提供的注册资料真实、准确、完整、合法有效，用户注册资料如有变动的，应及时更新其注册资料（包括但不限于地址变更、转让、更名等）。如果用户提供的注册资料不合法、不真实、不准确、不详尽的，用户需承担因此引起的相应责任及后果，并且本公司保留终止用户使用本网站各项服务的权利。<br/>
        　　
        5.2用户利用一发软件或者本网站进行活动时，其所登记、输入的信息、数据（包括但不限于姓名/名称、地址、联系电话、电子邮箱及经营数据等信息）均将进入本网站的后台系统，向本公司公开。除非得到用户的授权或法律另有规定，本网站不会向无关第三方披露用户隐私信息。<br/>
        　　 5.3本公司将根据需求，以不影响用户使用为前提，对上述3.2条款所列信息和数据进行整合等，用户对于上述3.2条款所列信息应确定可以向本网站公开。<br/> 　　
        5.4用户注册成功后，将产生用户名和密码等账户信息，您可以根据本公司规定改变您的密码。用户应谨慎合理的保存、使用其用户名和密码。用户若发现任何非法使用用户账号或存在安全漏洞的情况，请立即通知本公司并向公安机关报案。<br/>
        　　 5.5用户同意，本公司拥有通过邮件、短信、电话等形式，向用户、收货人、供应商等发送订单信息、促销活动等告知信息的权利。<br/> 　　
        5.6用户不得将注册获得的账户借给他人使用，否则用户应承担由此产生的全部责任，并与实际使用人承担连带责任。
    </div>
    <br/>

    <div class="areementList">
        　　第六条 货物信息
    </div>
    <div class="agreementContent">
        　　鉴于本网站上的货物信息（价格、数量、是否有货等）随时可能发生变动，本网站不作特别通知。由于网站上货物信息量极其庞杂，虽然本网站会尽最大努力保证货物信息的准确，但由于互联网技术因素等客观原因，相关信息可能会有一定的滞后性或差错，对此情形您知悉并予谅解。第七条
        订单与售后7.1用户下订单时，须仔细确认所购货物的名称、数量、价格、规格、尺寸、型号、联系方式、联系地址、收货人等所有信息。收货人与用户本人不一致的，收货人的行为和意思表示视为用户的行为和意思表示，用户应对收货人的行为及意思表示的法律后果承担连带责任。<br/>
        　　7.2双方明确：本网站上销售方展示的货物、价格信息仅为要约邀请，用户下单时须填写需要购买的货物数量、价款及支付方式、收货人、联系方式、收货地址、合同履行方式等内容；系统生成的订单信息是计算机信息系统根据用户填写的内容自动生成的数据，仅是用户向销售方发出的合同要约；销售方收到该订单信息后，已将货物实际直接向用户发出时，方视为用户与销售方之间就发出的货物建立了合同关系；如果用户在一份订单里订购了多种货物并且销售方只给用户发出了部分货物时，用户与销售方之间仅就实际向用户发出的货物建立了合同关系。<br/>
        　　7.3本公司要求销售方保证供应其展示的货物，确保交易的正常进行；但由于市场变化及其他难以控制的因素的影响，本公司无法保证用户提交的订单信息中希望购买的货物都会有货；如用户拟购买的货物，发生缺货，用户有权取消订单。<br/>
        　　7.4用户透过本网站购买的货物，将由销售者和本网站提供售后服务。
    </div>
    <br/>

    <div class="areementList">
        　　第八条 配送
    </div>
    <div class="agreementContent">
        　　 8.1本网站将会把货物送到您所指定的收货地址，所有在本网站上列出的送货时间为参考时间。<br/> 　　 8.2因如下情况造成订单延迟或无法配送等，本网站将不 承担延迟配送的责任：<br/> 　　
        （1）用户提供的信息错误（包括但不限于地址不详细、联系电话错误、停止使用等）原因导致的；<br/> 　　 （2）货物送达后无人签收，导致无法配送或延迟配送的；<br/> 　　 （3）情事变更因素导致的；<br/> 　　
        （4）不可抗力因素导致的，例如：自然灾害、交通戒严、突发战争、交通事故等。<br/> 　　 8.3运费负担
    </div>
    <br/>

    <div class="areementList">
        　　第九条 知识产权
    </div>
    <div class="agreementContent">
        　　9.1用户一旦接受本协议，即表明该用户主动将其在任何时间段在本网站发表的任何形式的信息内容（作品）授予本公司/本网站一项全球范围内的、非独家的、免费的、可转授权的使用此作品的许可（此许可使用权包括但不限于此作品的著作权、邻接权及其它财产权利），上述使用范围包括但不限于：<br/>
        　　 (a)其他用户可在本网站上对您上载、展示、张贴、传播的作品进行任意免费阅读；<br/> 　　 (b)本公司/本网站可复制，或对您上载、展示、张贴、传播的作品进行相关的市场推广；<br/> 　　
        (c)在必要的情况下，本公司/本网站对作品的形式可以进行必要的处理。<br/> 　　
        (d)用户确认已对作品的其他著作权人或相关权利人支付了费用或给予补偿，本公司/本网站无需再向任何著作权人或相关权利人支付费用或给予补偿。<br/>
        　　9.2用户承诺不将已发表于本网站的信息，以任何形式发布或授权其它主体以任何方式使用（包括但限于在各类网站、媒体上使用）。<br/>
        　　9.3本公司是本网站的制作者,拥有此网站内容及资源的著作权等合法权利,受国家法律保护,有权不时地对本协议及本网站的内容进行修改，并在本网站张贴，无须另行通知用户。在法律允许的最大限度范围内，本公司对本协议及本网站内容拥有解释权。<br/>
        　　9.4本网站所刊登的资料信息（诸如文字、图表、标识、按钮图标、图像、声音文件片段、数字下载、数据编辑和软件），均是本公司或其内容提供者的财产，受中国和国际版权法的保护。本网站上所有内容的汇编是本公司的排他财产，受中国和国际版权法的保护。本网站上所有软件都是本公司或其关联公司或其软件供应商的财产，受中国和国际版权法的保护。
    </div>
    <br/>

    <div class="areementList">
        　　第十条 责任免除及拒绝担保
    </div>
    <div class="agreementContent">
        　　除非另有明确的书面说明,本公司不对本网站的运营及其包含在本网站上的信息、内容、材料、产品（包括软件）或服务作任何形式的、明示或默示的声明或担保（根据中华人民共和国法律另有规定的以外）。本公司不担保本网站所包含的或以其它方式通过本网站提供给您的全部信息、内容、材料、产品（包括软件）和服务、其服务器或从本网站发出的电子信件、信息没有病毒或其他有害成分。如因不可抗力或诸如涉及的境内外基础电信运营商的移动通信网络的故障、技术缺陷、覆盖范围限制、不可抗力、计算机病毒、黑客攻击等其它本网站无法控制的原因使本网站销售系统崩溃或无法正常使用导致网上交易无法完成或丢失有关的信息、记录等，本公司不承担责任但会合理地尽力协助处理善后事宜。
    </div>
    <br/>

    <div class="areementList">
        　　第十一条 协议更新
    </div>
    <div class="agreementContent">
        　　根据国家法律法规变化及网站运营需要，本公司有权对本协议条款不时地进行修改，修改后的协议一旦被张贴在本网站上即生效，并代替原来的协议。用户可随时登陆查阅最新协议；用户有义务不时关注并阅读最新版的协议及网站公告。如用户不同意更新后的协议，可以且应立即停止接受本公司网站依据本协议提供的服务；如用户继续使用本网站提供的服务的，即视为同意更新后的协议。本网站建议您在使用本网站之前阅读本协议及本网站的公告。第11条争议解决就本协议的订立、履行及争议的解决均应适用在中华人民共和国大陆地区适用之有效法律。就本协议内容或其执行发生任何争议，双方应尽力友好协商解决；协商不成时，任何一方均可向本公司注册地有管辖权的中华人民共和国大陆地区法院提起诉讼。　
    </div>
    <br/>

    <div class="areementList">
        　　第十二条 其他
    </div>
    <div class="agreementContent">
        　　12.1本协议内容中以黑体、斜体等方式显著标识的条款，请用户着重阅读。<br/> 　　12.2如本协议中的任何条款无论因何种原因完全或部分无效或不具有执行力，本协议的其余条款仍应有效并且有约束力。<br/> 　　12.3
        本协议中的标题仅为方便而设，在解释本协议时应被忽略。<br/> 　　12.2您点击本协议上方的“同意以下协议，提交”按钮即视为您完全接受本协议，在点击之前请您再次确认已知悉并完全理解本协议的全部内容。
    </div>
    <br/>
    <br/><br/>

    <div class="agreementBtn">
        <input type="button" value="已阅读" onfocus="this.blur();" id="div_close"/>
    </div>
    <div class="areementThank">
        　　感谢您对我们工作的支持！
    </div>
</div>
<%@include file="/WEB-INF/views/footer_html.jsp" %>
</body>
</html>
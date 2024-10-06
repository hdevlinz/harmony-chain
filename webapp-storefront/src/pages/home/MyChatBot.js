import { useState } from 'react';
import ChatBot, { Button } from 'react-chatbotify';
import cookie from 'react-cookies';
import { findInvoiceByInvoiceNumber, findOrderByOrderNumber } from '../../configs/APIConfigs';
import { defaultImage, orderStatusName, orderTypesName } from '../../utils/Constatns';

const MyChatBot = () => {
   const [result, setResult] = useState(null);
   const [userSelection, setUserSelection] = useState('order');

   const input = {
      order: 'Đơn hàng',
      invoice: 'Hóa đơn',
   };

   const flow = {
      start: {
         message: () => {
            const seenBefore = cookie.load('user');
            if (seenBefore) {
               return `Chào mừng trở lại ${seenBefore?.data?.username}! Tôi có thể giúp gì cho bạn?`;
            }
            return 'Xin chào 👋! Tôi có thể giúp gì cho bạn?';
         },
         options: ['Đơn hàng', 'Hóa đơn'],
         chatDisabled: true,
         path: 'getInput',
      },
      getInput: {
         message: (params) => {
            const message = 'Để tìm mã đơn hàng/hóa đơn, vui lòng vào trang cá nhân -> đơn hàng';
            if (params.userInput === 'Đơn hàng') {
               setUserSelection('order');
               return `${message}\n\nVui lòng cung cấp mã đơn hàng:`;
            } else if (params.userInput === 'Hóa đơn') {
               setUserSelection('invoice');
               return `${message}\n\nVui lòng cung cấp mã hóa đơn:`;
            }
            return 'Vui lòng cung cấp thông tin cần thiết:';
         },
         function: async (params) => {
            switch (userSelection) {
               case 'order':
                  const order = await findOrderByOrderNumber(params.userInput);
                  setResult(order);
                  break;
               case 'invoice':
                  const invoice = await findInvoiceByInvoiceNumber(params.userInput);
                  setResult(invoice);
                  break;
               default:
                  setResult(null);
            }
         },
         path: 'displayResult',
      },
      displayResult: {
         message: () => {
            if (typeof result === 'string') {
               return result;
            }

            if (!result) {
               return 'Không tìm thấy thông tin. Vui lòng thử lại!';
            }

            return `Thông tin ${input[userSelection].toLowerCase()} của bạn:`;
         },
         component: () => {
            if (typeof result !== 'string') {
               switch (userSelection) {
                  case 'order':
                     return (
                        <div
                           style={{
                              marginTop: 10,
                              marginLeft: 20,
                              border: '1px solid #491d8d',
                              padding: 10,
                              borderRadius: 5,
                              maxWidth: 300,
                           }}
                        >
                           <p>Mã vận chuyển: {result.trackingNumber}</p>
                           <p>Tên người vận chuyển: {result.shipperName}</p>
                           <p>Loại: {orderTypesName[result.orderType]}</p>
                           <p>
                              Phí vận chuyển:{' '}
                              {result.shippingCost.toLocaleString('vi-VN', {
                                 style: 'currency',
                                 currency: 'VND',
                              })}
                           </p>
                           <p>Vị trí hiện tại: {result.currentLocation}</p>
                           <p>Trạng thái đơn hàng: {orderStatusName[result.orderStatus]}</p>
                           <p>Ngày giao hàng: {result.scheduledDate}</p>
                           <p>Ngày đặt hàng: {result.orderDate}</p>
                        </div>
                     );
                  case 'invoice':
                     return (
                        <div
                           style={{
                              marginTop: 10,
                              marginLeft: 20,
                              border: '1px solid #491d8d',
                              padding: 10,
                              borderRadius: 5,
                              maxWidth: 300,
                           }}
                        >
                           <p>Số hóa đơn: {result.invoiceNumber}</p>
                           <p>Tổng tiền: {result.totalAmount}</p>
                           <p>Ngày lập hóa đơn: {result.invoiceDate}</p>
                           <p>Trạng thái thanh toán: {result.paid ? 'Đã thanh toán' : 'Chưa thanh toán'}</p>
                        </div>
                     );
                  default:
                     return null;
               }
            }
         },
         options: ['Tạo mới'],
         chatDisabled: true,
         path: 'start',
      },
   };

   const settings = {
      tooltip: {
         mode: 'NEVER',
      },
      general: {
         primaryColor: '#009970',
         secondaryColor: '#009970',
         showFooter: false,
      },
      chatHistory: {
         viewChatHistoryButtonText: 'Tải tin nhắn trước đó ⟳',
         chatHistoryLineBreakText: '----- Tin nhắn trước đó -----',
      },
      chatInput: {
         enabledPlaceholderText: 'Nhập tin nhắn của bạn...',
         buttons: [Button.SEND_MESSAGE_BUTTON],
      },
      chatWindow: {
         messagePromptText: 'Tin nhắn mới ↓',
      },
      header: {
         title: (
            <div
               style={{ cursor: 'pointer', margin: 0, fontSize: 20, fontWeight: 'bold' }}
               onClick={() => window.open('https://github.com/HiepThanhTran/Website-SCMS')}
            >
               SCMS Harmony
            </div>
         ),
         showAvatar: true,
         avatar: defaultImage.USER_AVATAR,
         buttons: [Button.CLOSE_CHAT_BUTTON],
      },
      footer: {
         text: null,
         buttons: [Button.FILE_ATTACHMENT_BUTTON, Button.EMOJI_PICKER_BUTTON],
      },
   };

   return <ChatBot flow={flow} settings={settings} />;
};

export default MyChatBot;

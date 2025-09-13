# Database Schema Overview

## Các mối quan hệ chính đã thiết lập:

### 1. User Management
- **UserAccount** (1) ←→ (1) **UserProfile**
- **UserAccount** (1) ←→ (M) **Role** (Many-to-Many through user_roles)
- **UserProfile** (1) ←→ (M) **UserAddress**
- **UserProfile** (1) ←→ (1) **UserPreference**
- **UserProfile** (1) ←→ (1) **ShoppingCart**
- **UserProfile** (1) ←→ (M) **Order**
- **UserProfile** (1) ←→ (M) **Review**
- **UserProfile** (1) ←→ (M) **Notification**
- **UserProfile** (1) ←→ (M) **Transaction**

### 2. Product Management
- **Product** (M) ←→ (1) **Category**
- **Product** (M) ←→ (1) **Brand**
- **Product** (1) ←→ (M) **ProductVariant**
- **Product** (1) ←→ (M) **ProductImage**
- **Product** (1) ←→ (M) **Review**
- **ProductVariant** (1) ←→ (1) **Inventory**
- **ProductVariant** (1) ←→ (M) **CartItem**
- **ProductVariant** (1) ←→ (M) **OrderItem**

### 3. Category & Brand
- **Category** (1) ←→ (M) **Category** (self-referencing for parent-child)
- **Category** (1) ←→ (M) **Product**
- **Brand** (1) ←→ (M) **Product**

### 4. Shopping & Orders
- **ShoppingCart** (1) ←→ (M) **CartItem**
- **CartItem** (M) ←→ (1) **ProductVariant**
- **Order** (1) ←→ (M) **OrderItem**
- **Order** (M) ←→ (1) **UserProfile**
- **Order** (M) ←→ (1) **UserAddress** (shipping address)
- **Order** (M) ←→ (1) **Coupon** (optional)
- **Order** (1) ←→ (M) **Payment**
- **Order** (1) ←→ (M) **Transaction**
- **OrderItem** (M) ←→ (1) **ProductVariant**

### 5. Coupons & Payments
- **Coupon** (1) ←→ (M) **Order**
- **Payment** (M) ←→ (1) **Order**
- **Transaction** (M) ←→ (1) **Order**
- **Transaction** (M) ←→ (1) **UserProfile**

### 6. Security & Roles
- **Role** (M) ←→ (M) **Permission** (Many-to-Many through role_permissions)
- **UserAccount** (M) ←→ (M) **Role** (Many-to-Many through user_roles)
- **InvalidatedToken** (standalone for JWT blacklist)

## Các cải tiến đã thực hiện:

### 1. Coupon Entity
- ✅ Thêm `minimumOrderAmount`, `maximumDiscountAmount`
- ✅ Thêm `usedCount`, `isActive`
- ✅ Thay đổi `discountValue` từ Double sang BigDecimal
- ✅ Thêm quan hệ với Order

### 2. Order Entity
- ✅ Thêm quan hệ với Coupon
- ✅ Thêm quan hệ với UserAddress (shipping address)
- ✅ Thêm các field: `subtotal`, `discountAmount`, `shippingFee`

### 3. UserAddress Entity
- ✅ Thêm quan hệ ngược với Order

### 4. ProductVariant Entity
- ✅ Thêm quan hệ với Inventory (OneToOne)
- ✅ Thêm quan hệ với CartItem, OrderItem

### 5. Transaction Entity
- ✅ Thay đổi `amount` từ Double sang BigDecimal
- ✅ Thêm EqualsAndHashCode, ToString

### 6. Inventory Entity
- ✅ Thay đổi `inventoryId` từ String sang UUID

## Cascade Behaviors đã thiết lập:

### CascadeType.ALL:
- Product → ProductVariant
- Product → ProductImage
- Product → Review
- Category → subcategories
- Category → products
- ShoppingCart → CartItems
- Order → OrderItems
- Order → Payments
- Order → Transactions
- ProductVariant → Inventory

### Fetch Strategy:
- Hầu hết các quan hệ sử dụng **FetchType.LAZY** để tối ưu performance
- Một số quan hệ quan trọng có thể cần EAGER loading (như User roles)

## Notes quan trọng:
1. **BigDecimal** được sử dụng cho tất cả monetary values thay vì Double
2. **UUID** được sử dụng làm primary key cho hầu hết entities
3. **Soft delete** thông qua `isActive` field
4. **Auditing** với `createdAt`, `updatedAt` timestamps
5. **Proper indexing** cần được thiết lập cho foreign keys và search fields
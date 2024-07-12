# E-commerce Backend (Spring Boot)

This project is an evolving e-commerce backend system built with Spring Boot. Below is an overview of the current and planned features.

## Current Features

- User Management
- Product Management
- Shopping Cart
- Category Management (flattened table structure)
- Image Handling (small and large product images)
- SKU Management
- Discount System

## Planned Features and Enhancements

### User-related
- Verification system (boolean flag: isVerified)
- Verification code
- User types (Enum)
- Role-based permissions (Spring Security)
- Internal user ID (e.g., ktm123)
- Mobile number
- User status (Enum: active, inactive, blocked)
- Gender (male, female, other)
- Date of Birth
- Language preferences

### Notification System
- Types: promotion, orders, alerts, chat

### Address Management
- Many-to-one relationship with User
- Fields: city, country, state
- Full name, label (office, home)
- Default delivery address flag
- Contact number, name
- Address line 1 and 2

### Wishlist
- One-to-one relationship with User
- List of Products

### Product Enhancements
- Color options
- Size options (as an entity)
- Free shipping flag
- Add to wishlist functionality
- Delivery options (Enum: standard, express)
- Seller information
- Q&A section
- Review section
- Specifications (brand, SKU, seller-manipulated)
- Special discounts
- Reviews with like/dislike functionality

### Category System
- Three-level hierarchy

### Order and Delivery
- Delivery types (standard, express)
- Payment modes (COD, ESEWA)

### Color Management
- One-to-many composition
- Name (Enum)

### Additional Entities
- Q&A (priority feature)
- Specifications (including warranty information)

## Initial Class Diagram

![Initial Class Diagram](https://github.com/subhanshu1111/ecommerce-backend-springboot/assets/103764915/1d72e176-bb70-45a5-a401-29f3a10a6c4f)

Note: This diagram represents the initial design. Significant changes have been made since then.

## Contributing

We welcome contributions to this project. Please feel free to submit issues and pull requests.

## License

This project is licensed under the GNU Affero General Public License v3.0 (AGPLv3).

You are free to use, modify, and distribute this software, provided that:
1. You disclose the source code of any derivative works.
2. You license any derivative works under the same AGPLv3 license.
3. If you run a modified version of this software as a network service, you must make the complete source code of the modified version available to the users of that service.

For more details, see the [LICENSE](LICENSE) file in this repository or visit [https://www.gnu.org/licenses/agpl-3.0.en.html](https://www.gnu.org/licenses/agpl-3.0.en.html).
